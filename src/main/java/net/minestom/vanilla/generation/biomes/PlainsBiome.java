package net.minestom.vanilla.generation.biomes;

import de.articdive.jnoise.JNoise;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;

import java.util.Random;

public class PlainsBiome extends VanillaBiome implements ColumnGenerator {

	//TODO world seed
	public final JNoise heightMap = JNoise.newBuilder().openSimplex().setFrequency(0.25).setSeed(0).build();

	public PlainsBiome() {
		super(0.5f, 0.1f, 0, getMinecraftBiomeByName("plains"), null);
	}

	@Override
	public int getHeight(final int x, final int z) {
		return (int) (heightMap.getNoise(x / 16f, z / 16f) * 3 + 64);
	}

	@Override
	public int generate(final ChunkBatch batch, final int x, final int z, final int height, final Random r) {
		for (int y = 0; y <= height; y++)
			if (y == 0)
				batch.setBlock(x, y, z, Block.BEDROCK);
			else if (y < height - 4)
				batch.setBlock(x, y, z, Block.STONE);
			else if (y < height - 1)
				batch.setBlock(x, y, z, Block.DIRT);
			else
				batch.setBlock(x, y, z, Block.GRASS_BLOCK);
		return height;
	}

}
