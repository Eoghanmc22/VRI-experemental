package net.minestom.vanilla.placementRules;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.rule.BlockPlacementRule;

public abstract class VanillaPlacementRule extends BlockPlacementRule {

	public VanillaPlacementRule(short blockId) {
		super(blockId);
	}

	public VanillaPlacementRule(Block block) {
		super(block);
	}

}
