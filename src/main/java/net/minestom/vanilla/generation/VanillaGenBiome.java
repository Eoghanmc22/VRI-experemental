package net.minestom.vanilla.generation;

import lombok.Data;
import net.minestom.server.world.biomes.Biome;

@Data
public class VanillaGenBiome {

	final double temperature;
	final double humidity;
	final double height;
	final Biome biome;
	final VanillaBiomeData vanillaBiomeData;
}
