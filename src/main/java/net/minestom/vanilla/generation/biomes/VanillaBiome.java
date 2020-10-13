package net.minestom.vanilla.generation.biomes;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeManager;
import net.minestom.vanilla.generation.structures.PlaceableFeature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class VanillaBiome {

	final float temperature;
	final float humidity;
	final float rareness;
	final Biome biome;
	final ColumnGenerator generator;
	private List<PlaceableFeature> features = new ArrayList<>();

	public final static BiomeManager BIOME_MANAGER = MinecraftServer.getBiomeManager();

	public VanillaBiome(final float temperature, final float humidity, final float rareness, final Biome biome, final ColumnGenerator generator, final PlaceableFeature... features) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.rareness = rareness;
		this.biome = biome;
		this.generator = generator == null && this instanceof ColumnGenerator ? (ColumnGenerator) this : generator;
		if (features.length != 0) this.features = Arrays.asList(features);
	}

	public static Biome getMinecraftBiomeByName(final String name) {
		return BIOME_MANAGER.getByName(NamespaceID.from("minecraft:" + name));
	}

}
