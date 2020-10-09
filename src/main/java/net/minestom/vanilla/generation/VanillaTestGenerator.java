package net.minestom.vanilla.generation;

import de.articdive.jnoise.JNoise;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VanillaTestGenerator implements ChunkGenerator {

	public final Random random = new Random();
	//hills
	public final JNoise heightMap1 = JNoise.newBuilder().openSimplex().setFrequency(0.15).setSeed(random.nextInt()).build();
	public final JNoise heightMap2 = JNoise.newBuilder().openSimplex().setFrequency(0.25).setSeed(random.nextInt()).build();
	//detail
	public final JNoise heightMap3 = JNoise.newBuilder().openSimplex().setFrequency(0.5).setSeed(random.nextInt()).build();
	public final JNoise heightMap4 = JNoise.newBuilder().openSimplex().setFrequency(1).setSeed(random.nextInt()).build();
	public final JNoise heightMap5 = JNoise.newBuilder().openSimplex().setFrequency(2).setSeed(random.nextInt()).build();
	//oceans
	public final JNoise heightMap6 = JNoise.newBuilder().openSimplex().setFrequency(0.05).setSeed(random.nextInt()).build();
	//moutains
	public final JNoise heightMap7 = JNoise.newBuilder().openSimplex().setFrequency(0.003).setSeed(random.nextInt()).build();
	public final JNoise cavesMap = JNoise.newBuilder().openSimplex().setFrequency(0.5).setSeed(random.nextInt()).build();
	public final JNoise temperatureMap = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.001).setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(5).build();
	public final JNoise humidityMap = JNoise.newBuilder().octavated().setNoise(JNoise.newBuilder().openSimplex().setFrequency(0.001).setSeed(random.nextInt()).build()).setPersistence(0.2).setLacunarity(0.5).setOctaves(5).build();
	public final JNoise decorationsMap = JNoise.newBuilder().white().setSeed(random.nextInt()).build();
	public final ArrayList<VanillaGenBiome> biomes = new ArrayList<>();
	int seed = random.nextInt();

	public VanillaTestGenerator() {
		final BiomeManager bm = MinecraftServer.getBiomeManager();
		biomes.add(new VanillaGenBiome(0, 0, 0.2, bm.getByName(NamespaceID.from("minecraft:plains")),
				new VanillaBiomeData(Block.GRASS_BLOCK.getBlockId(), (short) -1, 0.01, VanillaBiomeData.fromShorts(Block.GRASS.getBlockId(), Block.TALL_GRASS.getBlockId(), Block.AIR.getBlockId(), Block.DANDELION.getBlockId()))));

		biomes.add(new VanillaGenBiome(-0.2, 0.3, 0.3, bm.getByName(NamespaceID.from("minecraft:forest")),
				new VanillaBiomeData(Block.GRASS_BLOCK.getBlockId(), (short) -1, 0.1, VanillaBiomeData.fromShorts(Block.GRASS.getBlockId(), Block.TALL_GRASS.getBlockId(), Block.AIR.getBlockId(), Block.DANDELION.getBlockId()))));

		biomes.add(new VanillaGenBiome(0, 2.0, 0.0, bm.getByName(NamespaceID.from("minecraft:ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(-0.3, 2.0, -0.2, bm.getByName(NamespaceID.from("minecraft:cold_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(-0.4, 2.0, -0.5, bm.getByName(NamespaceID.from("minecraft:deep_cold_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(-0.7, 2.0, -0.7, bm.getByName(NamespaceID.from("minecraft:deep_frozen_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(0.2, 2.0, -0.5, bm.getByName(NamespaceID.from("minecraft:deep_lukewarm_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(0, 2.0, -0.5, bm.getByName(NamespaceID.from("minecraft:deep_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(0.35, 2.0, -0.5, bm.getByName(NamespaceID.from("minecraft:deep_warm_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(-0.7, 2.0, -0.3, bm.getByName(NamespaceID.from("minecraft:frozen_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(0.25, 2.0, -0.3, bm.getByName(NamespaceID.from("minecraft:lukewarm_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));

		biomes.add(new VanillaGenBiome(0.35, 2.0, -0.3, bm.getByName(NamespaceID.from("minecraft:warm_ocean")),
				new VanillaBiomeData(Block.STONE.getBlockId(), (short) -1, 0.0, null)));
	}

	public static double square(double d) {
		return d * d;
	}

	public static void main(String[] args) {
		final VanillaTestGenerator vanillaTestGenerator = new VanillaTestGenerator();
		double min = Double.MAX_VALUE;
		double culminatedHeight = 0;
		double max = Double.MIN_VALUE;
		int times = 1000000;
		int l70 = 0;
		int g200 = 0;
		int g100 = 0;
		int l100 = 0;
		for (int i = 0; i < times; i++) {
			double height = vanillaTestGenerator.calculateHeight(vanillaTestGenerator.random.nextInt(), vanillaTestGenerator.random.nextInt()) + 64;
			culminatedHeight += height;
			if (height < min) {
				min = height;
			}
			if (height > max) {
				max = height;
			}
			if (height < 70) {
				l70++;
			}
			if (height > 200) {
				g200++;
			}
			if (height > 100) {
				g100++;
			} else {
				l100++;
			}
		}
		culminatedHeight /= times;
		System.out.println(max);
		System.out.println(min);
		System.out.println(culminatedHeight);
		System.out.println(g200);
		System.out.println(l70);
		System.out.println(g100);
		System.out.println(l100);
	}

	@Override
	public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {

		for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
			for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
				int posX = chunkX * 16 + x;
				int posZ = chunkZ * 16 + z;
				double heightDelta = calculateHeight(posX, posZ);
				int height = (int) (64 + heightDelta);

				VanillaGenBiome biome = getBiome(posX, posZ);

				batch.setBlock(x, 0, z, Block.BEDROCK);
				for (int level = 1; level < height; level++) {
					if (smoothRange(cavesMap.getNoise(posX / 16.0, level / 16.0, posZ / 16.0), -Math.max(level - (height - 10), 0), 0) < 0.5)
						batch.setBlock(x, level, z, Block.STONE);
				}
				for (int level = height; level < 64; level++) {
					//batch.setBlock(x, level, z, Block.WATER);
				}
				if (height > 64)
					for (int level = height - 3; level < height; level++) {
						batch.setBlock(x, level, z, Block.DIRT);
					}

				if (height >= 64) {
					batch.setBlockStateId(x, height - 1, z, biome.getVanillaBiomeData().getTopBlock());
					if (biome.getVanillaBiomeData().getOverlayBlock() != -1)
						batch.setBlockStateId(x, height, z, biome.getVanillaBiomeData().getOverlayBlock());
					if (random.nextDouble() < 0.3) {
						batch.setBlockStateId(x, height, z, biome.getVanillaBiomeData().getDecorationBlocks().getShort(random.nextInt(biome.getVanillaBiomeData().getDecorationBlocks().size())));
					}
					if (random.nextDouble() < biome.getVanillaBiomeData().getTreeChance()) {
						spawnTree(batch, x, height, z, Block.OAK_LOG, Block.OAK_LEAVES);
					}
				}
			}
		}
	}

	public double calculateHeight(int posX, int posZ) {
		return smoothRange(smoothRange(heightMap1.getNoise(posX / 16.0, posZ / 16.0) * 16, 1, -5) * 0.75 +
				smoothRange(heightMap2.getNoise(posX / 16.0, posZ / 16.0) * 16, 0, -6) * 0.5 +
				smoothRange(heightMap3.getNoise(posX / 16.0, posZ / 16.0) * 16, 0, 1) * 0.3 +
				smoothRange(heightMap4.getNoise(posX / 16.0, posZ / 16.0) * 16, 0, 0) * 0.15 +
				smoothRange(heightMap5.getNoise(posX / 16.0, posZ / 16.0) * 16, 0, 0) * 0.1 +
				smoothRange(heightMap6.getNoise(posX / 16.0, posZ / 16.0) * 16, 7, 20) * 0.5 +
				(smoothRange(square(heightMap7.getNoise(posX / 16.0, posZ / 16.0) * 16), 10, -10) - 15) * 0.3, 0, -1);
	}

	public double smoothRange(double value, double top, double bottom) {
		return value >= 0 ? (value + top * (value / 6)) : value - bottom * (-value / 6);
	}

	@Override
	public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
		Biome[] tempBiomes = new Biome[16];
		int biomeSize = 4;
		for (int i = 0; i < biomeSize; i++) {
			for (int j = 0; j < biomeSize; j++) {
				tempBiomes[i * 4 + j] = getBiome(chunkX * 16 + j, chunkZ * 16 + i).getBiome();
			}
		}
		int temp = (int) square((double) Chunk.CHUNK_SIZE_X / biomeSize);
		for (int i = 0; i < biomes.length; i++) {
			biomes[i] = tempBiomes[i % temp];
		}
	}

	public double getHeight(int x, int z) {
		return Math.max(-1, Math.min(calculateHeight(x, z) / 32, 1));
	}

	public double getTemperature(int x, int z) {
		double temperature = temperatureMap.getNoise(x, z);
		temperature -= getHeight(x, z) * 0.05;
		return temperature;
	}

	public double getHumidity(int x, int z) {
		double humidity = humidityMap.getNoise(x, z);
		if (getHeight(x, z) < 0)
			humidity = 2;
		else {
			humidity = getHeight(x, z) / 2 + humidity / 2;
		}
		return humidity;
	}

	public VanillaGenBiome getBiome(int x, int z) {
		double height = getHeight(x, z);
		double temperature = getTemperature(x, z);
		double humidity = getHumidity(x, z);
		double distance = Double.MAX_VALUE;
		VanillaGenBiome biome = null;
		for (final VanillaGenBiome biomeT : biomes) {
			double tempDistance = square(biomeT.getTemperature() - temperature) + square(biomeT.getHumidity() - humidity) + square(biomeT.getHeight() - height);
			if (distance > tempDistance) {
				distance = tempDistance;
				biome = biomeT;
			}
		}
		return biome;
	}

	private void spawnTree(ChunkBatch batch, int trunkX, int trunkBottomY, int trunkZ, Block log, Block leaves) {

		for (int i = 0; i < 2; i++) {
			for (int x = -2; x <= 2; x++) {
				for (int z = -2; z <= 2; z++) {
					batch.setBlock(trunkX + x, trunkBottomY + 2 + i, trunkZ - z, leaves);
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			batch.setBlock(trunkX + 1, trunkBottomY + 3 + i, trunkZ, leaves);
			batch.setBlock(trunkX - 1, trunkBottomY + 3 + i, trunkZ, leaves);
			batch.setBlock(trunkX, trunkBottomY + 3 + i, trunkZ + 1, leaves);
			batch.setBlock(trunkX, trunkBottomY + 3 + i, trunkZ - 1, leaves);
		}

		batch.setBlock(trunkX, trunkBottomY, trunkZ, log);
		batch.setBlock(trunkX, trunkBottomY + 1, trunkZ, log);
		batch.setBlock(trunkX, trunkBottomY + 2, trunkZ, log);
		batch.setBlock(trunkX, trunkBottomY + 3, trunkZ, log);
		batch.setBlock(trunkX, trunkBottomY + 4, trunkZ, log);
		batch.setBlock(trunkX, trunkBottomY + 5, trunkZ, leaves);
	}

	@Override
	public List<ChunkPopulator> getPopulators() {
		return null;
	}

}
