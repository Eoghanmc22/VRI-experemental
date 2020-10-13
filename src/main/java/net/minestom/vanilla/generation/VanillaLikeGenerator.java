package net.minestom.vanilla.generation;

import de.articdive.jnoise.JNoise;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.vanilla.generation.biomes.VanillaBiome;
import net.minestom.vanilla.generation.biomes.VanillaBiomes;
import net.minestom.vanilla.generation.structures.PlaceableFeature;
import net.minestom.vanilla.generation.structures.VanillaStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VanillaLikeGenerator implements SimpleChunkGenerator {

	public static final ArrayList<VanillaBiome> biomes = new ArrayList<>();

	static {
		VanillaBiomes.registerAll();
		VanillaStructures.registerAll();
	}

	public final Random random = new Random();
	public final JNoise temperatureMap = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.1)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(3).build();
	public final JNoise humidityMap = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.1)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(3).build();
	public final JNoise weirdnessMap1 = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.1)
			.setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(3).build();
	public final VoronoiNoise biomeMap = new VoronoiNoise();

	{
		biomeMap.setSeed(random.nextInt());
	}

	@Override
	public void generateChunkData(final ChunkBatch batch, final int chunkX, final int chunkZ) {
		random.setSeed((long) chunkX << 32 | chunkZ);
		for (int x1 = 0; x1 < 4; x1++)
			for (int z1 = 0; z1 < 4; z1++)
				for (int x2 = 0; x2 < 4; x2++)
					for (int z2 = 0; z2 < 4; z2++) {
						final int x = x1 * 4 + x2;
						final int z = z1 * 4 + z2;
						final VoronoiNoise.Output output = getBiomeOutput(chunkX * 16 + x, chunkZ * 16 + z);
						final VanillaBiome biome = getBiome(output);
						final boolean shouldSmooth = shouldSmooth(chunkX * 16 + x, chunkZ * 16 + z);
						final int heightPre = shouldSmooth ? getAverageHeight(chunkX * 16 + x, chunkZ * 16 + z) : biome.getGenerator().getHeight(chunkX * 16 + x, chunkZ * 16 + z);
						final int height = biome.getGenerator().generate(batch, x, z, heightPre, random);
						for (final PlaceableFeature feature : biome.getFeatures())
							if (feature.chance(biome) >= random.nextFloat())
								feature.place(batch, new BlockPosition(x, height + 1, z), biome, random);
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
		return biomeMap.getNoise(x / 64f, z / 64f);
	}

	public boolean shouldSmooth(final int x, final int z) {
		float average = 0;
		final int range = 6;
		int count = 0;
		boolean allow = false;
		final VoronoiNoise.Output current = getBiomeOutput(x, z);
		final NamespaceID biomeName = getBiome(current).getBiome().getName();
		for (int x2 = -range; x2 <= range; x2++)
			for (int z2 = -range; z2 <= range; z2++) {
				if (x2 == 0 && z2 == 0)
					continue;
				final VoronoiNoise.Output offset = getBiomeOutput(x + x2, z + z2);
				average += offset.getDistance();
				allow = !getBiome(offset).getBiome().getName().equals(biomeName) || allow;
				count++;
			}
		average /= count;
		return average < current.getDistance() && allow;
	}

	public int getAverageHeight(final int x, final int z) {
		int average = 0;
		final int range = 2;
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int count = 0;
		for (int x2 = -range; x2 <= range; x2++)
			for (int z2 = -range; z2 <= range; z2++) {
				count++;
				final int height = getBiome(x + x2, z + z2).getGenerator().getHeight(x + x2, z + z2);
				average += height;
				max = Math.max(max, height);
				min = Math.min(min, height);
			}
		average /= count;
		average = (average * 2 + max) / 3;
		return average;
	}

	public int getHeight(final int x, final int z) {
		return getBiome(x, z).getGenerator().getHeight(x, z);
	}

	public double getTemperature(final int x, final int z) {
		return temperatureMap.getNoise(x, z);
	}

	public double getHumidity(final int x, final int z) {
		return humidityMap.getNoise(x, z);
	}

	public double getWeirdness(final int x, final int z) {
		return weirdnessMap1.getNoise(x, z);
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
