package net.minestom.vanilla.generation.structures;

import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.vanilla.generation.biomes.VanillaBiome;
import net.minestom.vanilla.generation.biomes.VanillaBiomes;

import java.util.Random;

public class TreeStructure implements PlaceableFeature {

	@Override
	public void place(final ChunkBatch batch, final BlockPosition bpos, final VanillaBiome biome, final Random r) {
		if (VanillaBiomes.PLAINS.getBiome().equals(biome))
			spawnTree(batch, bpos.getX(), bpos.getY(), bpos.getZ(), 2, Block.OAK_LOG, Block.OAK_LEAVES, r);
	}

	@Override
	public float chance(final VanillaBiome biome) {
		if (VanillaBiomes.PLAINS.getBiome().equals(biome))
			return 0.001f;
		return 0.002f;
	}

	private void spawnTree(final ChunkBatch batch, final int trunkX, final int trunkY, final int trunkZ, final int height, final Block log, final Block leaves, final Random r) {
		for (int x = -2; x <= 2; x++)
			for (int y = 0; y < 2; y++)
				for (int z = -2; z <= 2; z++) batch.setBlock(trunkX + x, trunkY + y + height, trunkZ - z, leaves);
		for (int i = 0; i < 2; i++) {
			batch.setBlock(trunkX + 1, trunkY + 2 + i + height, trunkZ, leaves);
			batch.setBlock(trunkX - 1, trunkY + 2 + i + height, trunkZ, leaves);
			batch.setBlock(trunkX, trunkY + 2 + i + height, trunkZ + 1, leaves);
			batch.setBlock(trunkX, trunkY + 2 + i + height, trunkZ - 1, leaves);
		}

		batch.setBlock(trunkX, trunkY, trunkZ, log);
		batch.setBlock(trunkX, trunkY + 1, trunkZ, log);
		batch.setBlock(trunkX, trunkY + 2, trunkZ, log);
		batch.setBlock(trunkX, trunkY + 3, trunkZ, log);
		batch.setBlock(trunkX, trunkY + 4, trunkZ, log);
		batch.setBlock(trunkX, trunkY + 5, trunkZ, leaves);
	}

}
