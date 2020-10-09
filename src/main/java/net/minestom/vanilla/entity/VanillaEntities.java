package net.minestom.vanilla.entity;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum VanillaEntities {
	PIG(VanillaPig::new);

	@Getter
	final BiFunction<Position, Instance, EntityCreature> entityFunction;

	VanillaEntities(BiFunction<Position, Instance, EntityCreature> entityFunction) {
		this.entityFunction = entityFunction;
	}

	//todo
	public static void registerAll() {
		Random rng = new Random();
		MinecraftServer.getSchedulerManager().buildTask(() -> {
			for (final Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
				if (player.getInstance().getEntities().size() > -1) {
					return;
				}
				for (final Chunk chunk : player.getViewableChunks()) {
					for (final VanillaEntities ve : values()) {
						if (rng.nextFloat()<=0.5f) {
							ve.entityFunction.apply(new Position(rng.nextInt(16) + chunk.getChunkX()*16, 90, rng.nextInt(16) + chunk.getChunkZ()*16), player.getInstance());
						}
					}
				}
			}
		}).repeat(50, TimeUnit.TICK).schedule();
	}
}
