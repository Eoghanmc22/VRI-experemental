package net.minestom.vanilla.generation;

import de.articdive.jnoise.JNoise;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.world.biomes.Biome;
import net.minestom.vanilla.generation.biomes.VanillaBiome;
import net.minestom.vanilla.generation.biomes.VanillaBiomes;
import net.minestom.vanilla.generation.structures.PlaceableFeature;
import net.minestom.vanilla.generation.structures.VanillaStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VanillaLikeGenerator implements SimpleChunkGenerator {

	public final Random random = new Random();
	public static final ArrayList<VanillaBiome> biomes = new ArrayList<>();

	public final JNoise temperatureMap = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.001)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(5).build();
	public final JNoise humidityMap = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.001)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(5).build();
	public final JNoise weirdnessMap1 = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.001)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(5).build();
	public final JNoise weirdnessMap2 = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.01)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(5).build();

	public final VoronoiNoise biomeMap = new VoronoiNoise();

	{
		biomeMap.setSeed(random.nextInt());
	}

	static {
		VanillaBiomes.registerAll();
		VanillaStructures.registerAll();
	}

	@Override
	public void generateChunkData(final ChunkBatch batch, final int chunkX, final int chunkZ) {
		random.setSeed((long) chunkX << 32 | chunkZ);
		for (int x1 = 0; x1 < 4; x1++)
			for (int z1 = 0; z1 < 4; z1++) {
				final VoronoiNoise.Output output = getBiomeOutput(chunkX * 16 + x1 * 4, chunkZ * 16 + z1 * 4);
				final VanillaBiome biome = getBiome(output);
				for (int x2 = 0; x2 < 4; x2++)
					for (int z2 = 0; z2 < 4; z2++) {
						final int x = x1 * 4 + x2;
						final int z = z1 * 4 + z2;
						final float adjustment = (float) Math.min(Math.sqrt(output.getDistance()), 1);
						final int heightPre = (int) (biome.getGenerator().getHeight(chunkX * 16 + x, chunkZ * 16 + z) * (1 - adjustment) + getHeight(chunkX * 16 + x, chunkZ * 16 + z) * adjustment);
						final int height = biome.getGenerator().generate(batch, x, z, heightPre, random);
						for (final PlaceableFeature feature : biome.getFeatures())
							if (feature.chance() <= random.nextFloat())
								feature.place(batch, new BlockPosition(x, height + 1, z), biome, random);
					}
			}
	}

	@Override
	public List<ChunkPopulator> getPopulators() {
		return null;
	}

	public VanillaBiome getBiome(final float xP, final float zP) {
		return getBiome(getBiomeOutput(xP, zP));
	}

	public VanillaBiome getBiome(final VoronoiNoise.Output output) {
		final int x = (int) Math.floor(output.getPoint().getX());
		final int z = (int) Math.floor(output.getPoint().getY());
		final double weirdness = getWeirdness(x, z);
		final double temperature = getTemperature(x, z);
		final double humidity = getHumidity(x, z);
		double distance = Double.MAX_VALUE;
		VanillaBiome biome = null;
		for (final VanillaBiome biomeT : biomes) {
			final double tempDistance = square(biomeT.getTemperature() - temperature) + square(biomeT.getHumidity() - humidity) + square(biomeT.getRareness() - weirdness);
			if (distance > tempDistance) {
				distance = tempDistance;
				biome = biomeT;
			}
		}
		return biome;
	}

	public VoronoiNoise.Output getBiomeOutput(final float x, final float z) {
		return biomeMap.getNoise(x / 160f, z / 160f);
	}

	public double getHeight(final int x, final int z) {
		return 70;
	}

	public double getTemperature(final int x, final int z) {
		return temperatureMap.getNoise(x / 16f, z / 16f);
	}

	public double getHumidity(final int x, final int z) {
		return humidityMap.getNoise(x / 16f, z / 16f);
	}

	public double getWeirdness(final int x, final int z) {
		return weirdnessMap1.getNoise(x / 16f, z / 16f) / 2 + weirdnessMap2.getNoise(x / 16f, z / 16f) / 2;
	}

	@Override
	public Biome getBiome2D(final int x, final int z) {
		return getBiome(x, z).getBiome();
	}

	//UTIL

	public double square(final double n) {
		return n * n;
	}

}
