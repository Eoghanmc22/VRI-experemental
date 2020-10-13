package net.minestom.vanilla.generation.structures;

import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.utils.BlockPosition;
import net.minestom.vanilla.generation.biomes.VanillaBiome;

import java.util.Random;

public interface PlaceableFeature {

	void place(ChunkBatch batch, BlockPosition bpos, VanillaBiome biome, Random r);

	float chance();

}
