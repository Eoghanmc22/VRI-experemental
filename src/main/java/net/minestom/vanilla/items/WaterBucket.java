package net.minestom.vanilla.items;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Direction;
import net.minestom.vanilla.blocks.VanillaBlocks;

public class WaterBucket extends VanillaItem {

	public WaterBucket() {
		super(Material.WATER_BUCKET);
	}

	@Override
	public void onUseInAir(Player player, ItemStack itemStack, Player.Hand hand) {}

	@Override
	public boolean onUseOnBlock(Player player, ItemStack itemStack, Player.Hand hand, BlockPosition position, Direction blockFace) {
		if (!player.getInstance().getDimensionType().isUltrawarm())
			//player.getInstance().setCustomBlock(position.clone().add(blockFace.normalX(), blockFace.normalY(), blockFace.normalZ()), VanillaBlocks.WATER.getInstance().getIdentifier());
			player.getInstance().setBlock(position.clone().add(blockFace.normalX(), blockFace.normalY(), blockFace.normalZ()), Block.WATER);
		//player.setItemInHand(hand, new ItemStack(Material.BUCKET, (byte) 1));
		return true;
	}

}
