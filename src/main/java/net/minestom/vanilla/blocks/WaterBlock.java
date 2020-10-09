package net.minestom.vanilla.blocks;

import net.minestom.server.data.Data;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;

public class WaterBlock extends VanillaBlock {

	public WaterBlock() {
		super(Block.WATER);
	}

	@Override
	public void update(Instance instance, BlockPosition blockPosition, Data data) {
		int sourceBlocks = 0;
		int distance = 16;
		int currentlevel = Integer.parseInt(Block.fromStateId(instance.getBlockStateId(blockPosition)).getAlternative(instance.getBlockStateId(blockPosition)).getProperties()[0].substring(6));
		currentlevel = currentlevel >= 8 ? currentlevel - 8 : currentlevel;
		if (currentlevel != 0) {
			for (final Direction d : Direction.values()) {
				if (d == Direction.DOWN)
					continue;
				BlockPosition bposT = blockPosition.clone().add(d.normalX(), d.normalY(), d.normalZ());
				if (Block.fromStateId(instance.getBlockStateId(bposT)) == Block.WATER) {
					if (d == Direction.UP) {
						distance = 1;
						continue;
					}
					int level = Integer.parseInt(Block.WATER.getAlternative(instance.getBlockStateId(bposT)).getProperties()[0].substring(6));
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
				if (Block.fromStateId(instance.getBlockStateId(blockPosition.clone().subtract(0, 1, 0))).isAir())
					instance.setSeparateBlocks(blockPosition, Block.WATER.withProperties("level=" + (newLevel + 8)), getCustomBlockId());
				else
					instance.setSeparateBlocks(blockPosition, Block.WATER.withProperties("level=" + (newLevel)), getCustomBlockId());
		}
		final BlockPosition down = blockPosition.clone().add(0, -1, 0);
		if (Block.fromStateId(instance.getBlockStateId(down)) == Block.AIR)
			instance.setSeparateBlocks(down, Block.WATER.withProperties("level=" + 1), getCustomBlockId());
		else if (Block.fromStateId(instance.getBlockStateId(down)) != Block.WATER)
			for (final Direction d : Direction.values()) {
				if (d == Direction.UP || d == Direction.DOWN)
					continue;
				BlockPosition bposT = blockPosition.clone().add(d.normalX(), d.normalY(), d.normalZ());
				if (Block.fromStateId(instance.getBlockStateId(bposT)) == Block.WATER) {
					int level = Integer.parseInt(Block.WATER.getAlternative(instance.getBlockStateId(bposT)).getProperties()[0].substring(6));
					level = level >= 8 ? level - 8 : level;
					if (level > currentlevel + 1)
						if ((currentlevel + 1) <= 7)
							instance.setSeparateBlocks(bposT, Block.WATER.withProperties("level=" + (currentlevel + 1)), getCustomBlockId());
				} else if (Block.fromStateId(instance.getBlockStateId(bposT)).isAir())
					if ((currentlevel + 1) <= 7)
						instance.setSeparateBlocks(bposT, Block.WATER.withProperties("level=" + (currentlevel + 1)), getCustomBlockId());
			}
	}

	@Override
	protected BlockPropertyList createPropertyValues() {
		return new BlockPropertyList().intRange("level", 0, 15);
	}

	@Override
	public UpdateOption getUpdateOption() {
		return new UpdateOption(10, TimeUnit.TICK);
	}

	@Override
	public void updateFromNeighbor(Instance instance, BlockPosition thisPosition, BlockPosition neighborPosition, boolean directNeighbor) {

	}

}
