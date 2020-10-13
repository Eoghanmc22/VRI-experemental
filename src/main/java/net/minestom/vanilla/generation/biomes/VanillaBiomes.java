package net.minestom.vanilla.generation.biomes;

import lombok.Getter;
import net.minestom.vanilla.generation.VanillaLikeGenerator;

@Getter
public enum VanillaBiomes {
	PLAINS(new PlainsBiome());

	final VanillaBiome biome;

	VanillaBiomes(final VanillaBiome biome) {
		this.biome = biome;
	}

	public static void registerAll() {
		for (final VanillaBiomes biome : values()) VanillaLikeGenerator.biomes.add(biome.getBiome());
	}
}
