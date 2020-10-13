package net.minestom.vanilla.generation.biomes;

import net.minestom.server.instance.batch.ChunkBatch;

import java.util.Random;

public interface ColumnGenerator {

	int getHeight(int x, int z);

	int generate(ChunkBatch batch, int x, int z, int height, Random r);


	//TODO improve
	default double smoothRange(final double value, final double top, final double bottom) {
		return value >= 0 ? (value + top * (value / 6)) : value + bottom * (value / 6);
	}

}
