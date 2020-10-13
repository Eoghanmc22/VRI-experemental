package net.minestom.vanilla.generation.biomes;

import de.articdive.jnoise.JNoise;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.vanilla.generation.structures.PlaceableFeature;

import java.util.Random;

public class DesertBiome extends VanillaBiome implements ColumnGenerator, PlaceableFeature {

	//TODO world seed
	public final JNoise heightMap = JNoise.newBuilder().openSimplex().setFrequency(0.25).setSeed(0).build();

	public DesertBiome() {
		super(0.5f, -0.5f, 0, getMinecraftBiomeByName("desert"), null);
	}

	@Override
	public int getHeight(final int x, final int z) {
		return (int) (heightMap.getNoise(x / 16f, z / 16f) * 8 + 70);
	}

	@Override
	public int generate(final ChunkBatch batch, final int x, final int z, final int height, final Random r) {
		for (int y = 0; y <= height; y++)
			if (y == 0)
				batch.setBlock(x, y, z, Block.BEDROCK);
			else if (y < height - 14)
				batch.setBlock(x, y, z, Block.STONE);
			else if (y < height - 5)
				batch.setBlock(x, y, z, Block.SANDSTONE);
			else
				batch.setBlock(x, y, z, Block.SAND);
		return height;
	}

	@Override
	public void place(final ChunkBatch batch, final BlockPosition bpos, final VanillaBiome biome, final Random r) {
		switch (r.nextInt(2)) {
			case 0: {
				batch.setBlock(bpos, Block.DEAD_BUSH);
				break;
			}
			case 1: {
				for (int i = 0; i < r.nextInt(5); i++) batch.setBlock(bpos.clone().add(0, i, 0), Block.CACTUS);
				break;
			}
		}
	}

	@Override
	public float chance(final VanillaBiome biome) {
		return 0.001f;
	}

}
