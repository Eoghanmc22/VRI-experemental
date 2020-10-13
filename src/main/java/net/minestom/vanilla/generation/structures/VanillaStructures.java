package net.minestom.vanilla.generation.structures;

import lombok.Getter;
import net.minestom.vanilla.generation.biomes.DesertBiome;
import net.minestom.vanilla.generation.biomes.VanillaBiomes;

@Getter
public enum VanillaStructures {
	TREE(new TreeStructure(), VanillaBiomes.PLAINS),
	DESERT_FEATURES(new DesertBiome(), VanillaBiomes.DESERT);

	final PlaceableFeature structure;
	final VanillaBiomes[] biomes;

	VanillaStructures(final PlaceableFeature structure, final VanillaBiomes... biomes) {
		this.structure = structure;
		this.biomes = biomes;
	}

	public static void registerAll() {
		for (final VanillaStructures structure : values())
			for (final VanillaBiomes biomes : structure.getBiomes())
				biomes.getBiome().getFeatures().add(structure.getStructure());
	}
}
