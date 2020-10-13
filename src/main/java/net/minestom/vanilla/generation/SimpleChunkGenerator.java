package net.minestom.vanilla.generation;

import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.utils.MathUtils;
import net.minestom.server.world.biomes.Biome;

public interface SimpleChunkGenerator extends ChunkGenerator {

	@Override
	default void fillBiomes(final Biome[] biomes, final int chunkX, final int chunkZ) {
		final Biome[] tempBiomes = new Biome[16];
		final int biomeSize = 4;
		for (int i = 0; i < biomeSize; i++)
			for (int j = 0; j < biomeSize; j++) tempBiomes[i * 4 + j] = getBiome2D(chunkX * 16 + j, chunkZ * 16 + i);
		final int temp = MathUtils.square(Chunk.CHUNK_SIZE_X / biomeSize);
		for (int i = 0; i < biomes.length; i++) biomes[i] = tempBiomes[i % temp];
	}

	default Biome getBiome2D(final int x, final int z) {
		return Biome.PLAINS;
	}

}
