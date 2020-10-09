package net.minestom.vanilla.placementRules;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskBuilder;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;
import net.minestom.server.utils.time.TimeUnit;

public class WaterPlacementRule extends VanillaPlacementRule {

	public WaterPlacementRule() {
		super(Block.WATER);
	}

	@Override
	public boolean canPlace(Instance instance, BlockPosition blockPosition) {
		return true;
	}

	@Override
	public short blockRefresh(Instance instance, BlockPosition blockPosition, short currentStateID_IN) {
		scheduleFasterTask(() -> {
			int sourceBlocks = 0;
			int distance = 16;
			short currentStateID = instance.getBlockStateId(blockPosition);
			if (Block.fromStateId(currentStateID) != Block.WATER)
				return;
			int currentlevel = Integer.parseInt(Block.fromStateId(currentStateID).getAlternative(currentStateID).getProperties()[0].substring(6));
			currentlevel = currentlevel >= 8 ? currentlevel - 8 : currentlevel;
			final BlockPosition down = blockPosition.clone().subtract(0, 1, 0);
			final short blockStateIdDown = instance.getBlockStateId(down);
			if (currentlevel != 0) {
				for (final Direction d : Direction.values()) {
					if (d == Direction.DOWN)
						continue;
					BlockPosition bposT = blockPosition.clone().add(d.normalX(), d.normalY(), d.normalZ());
					final short blockStateId = instance.getBlockStateId(bposT);
					if (Block.fromStateId(blockStateId) == Block.WATER) {
						if (d == Direction.UP) {
							distance = 1;
							continue;
						}
						int level = Integer.parseInt(Block.WATER.getAlternative(blockStateId).getProperties()[0].substring(6));
						level = level >= 8 ? level - 8 : level;
						if (level == 0)
							sourceBlocks++;
						if (level < distance)
							distance = level;
					}
				}
				int newLevel;
				if (sourceBlocks > 1)
					newLevel = 0;
				else if (distance + 1 < 8)
					newLevel = distance + 1;
				else {
					instance.setBlock(blockPosition, Block.AIR);
					return;
				}
				if (newLevel != currentlevel)
					if (Block.fromStateId(blockStateIdDown).isAir()) {
						final short block = Block.WATER.withProperties("level=" + (newLevel + 8));
						if (block != currentStateID)
							instance.setBlockStateId(blockPosition, Block.WATER.withProperties("level=" + (newLevel + 8)));
					} else {
						final short block = Block.WATER.withProperties("level=" + (newLevel));
						if (block != currentStateID)
							instance.setBlockStateId(blockPosition, Block.WATER.withProperties("level=" + (newLevel)));
					}
			}
			if (Block.fromStateId(blockStateIdDown) == Block.AIR)
				instance.setBlockStateId(down, Block.WATER.withProperties("level=" + 1));
			else if (Block.fromStateId(blockStateIdDown) != Block.WATER)
				for (final Direction d : Direction.values()) {
					if (d == Direction.UP || d == Direction.DOWN)
						continue;
					BlockPosition bposT = blockPosition.clone().add(d.normalX(), d.normalY(), d.normalZ());
					final short blockStateIdT = instance.getBlockStateId(bposT);
					if (Block.fromStateId(blockStateIdT) == Block.WATER) {
						int level = Integer.parseInt(Block.WATER.getAlternative(blockStateIdT).getProperties()[0].substring(6));
						level = level >= 8 ? level - 8 : level;
						if (level > currentlevel + 1)
							if ((currentlevel + 1) <= 7)
								instance.setBlockStateId(bposT, Block.WATER.withProperties("level=" + (currentlevel + 1)));
					} else if (Block.fromStateId(blockStateIdT).isAir())
						if ((currentlevel + 1) <= 7)
							instance.setBlockStateId(bposT, Block.WATER.withProperties("level=" + (currentlevel + 1)));
				}
		}, 0, TimeUnit.TICK);
		return currentStateID_IN;
	}

	@Override
	public short blockPlace(Instance instance, Block block, BlockFace blockFace, Player pl) {
		return Block.WATER.getBlockId();
	}

	public static final SchedulerManager sm = MinecraftServer.getSchedulerManager();
	public void scheduleFasterTask(Runnable r, long delay, TimeUnit unit) {
		sm.getTimerExecutionService().schedule(r, unit.toMilliseconds(delay), java.util.concurrent.TimeUnit.MILLISECONDS);
	}
}
