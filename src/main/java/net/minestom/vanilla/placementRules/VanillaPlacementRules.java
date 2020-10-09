package net.minestom.vanilla.placementRules;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.block.BlockManager;

public enum VanillaPlacementRules {
	WATER(new WaterPlacementRule());

	@Getter
	private final VanillaPlacementRule rule;

	VanillaPlacementRules(VanillaPlacementRule rule) {
		this.rule = rule;
	}

	public static void registerAll() {
		final BlockManager blockManager = MinecraftServer.getBlockManager();
		for (VanillaPlacementRules rule : values()) {
			blockManager.registerBlockPlacementRule(rule.getRule());
		}
	}
}
