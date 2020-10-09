package net.minestom.vanilla.blocks;

import net.minestom.server.data.Data;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;

import java.util.ArrayList;

public class SpongeBlock extends VanillaBlock {

	public SpongeBlock() {
		super(Block.SPONGE);
	}

	@Override
	public void onPlace(Instance instance, BlockPosition blockPosition, Data data) {
		ArrayList<BlockPosition> blocks = new ArrayList<>();
		if (!instance.getDimensionType().isUltrawarm())
			if (drainWater(instance, blockPosition, 0, 0, blocks) > 0) {
				for (final BlockPosition bposT : blocks) {
					instance.setBlock(bposT, Block.AIR);
				}
				instance.setBlock(blockPosition, Block.WET_SPONGE);
			}
	}

	private int drainWater(Instance instance, BlockPosition blockPosition, int waterDrained, int depth, ArrayList<BlockPosition> blocksOut) {
		if (waterDrained > 15 || depth > 5)
			return waterDrained;
		ArrayList<BlockPosition> blocks = new ArrayList<>();
		for (final Direction d : Direction.values()) {
			BlockPosition bposT = blockPosition.clone().add(d.normalX(), d.normalY(), d.normalZ());
			if (Block.fromStateId(instance.getBlockStateId(bposT)) == Block.WATER) {
				blocksOut.add(bposT);
				blocks.add(bposT);
				waterDrained++;
			}
		}
		depth++;
		for (final BlockPosition bposT : blocks) {
			drainWater(instance, bposT, waterDrained, depth, blocksOut);
		}
		return blocksOut.size();
	}

	@Override
	protected BlockPropertyList createPropertyValues() {
		return new BlockPropertyList();
	}

}
