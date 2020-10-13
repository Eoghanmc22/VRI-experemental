package net.minestom.vanilla.generation.old;

import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VanillaBiomeData {

	short topBlock;
	short overlayBlock;
	double treeChance;
	ShortArrayList decorationBlocks;

	public static ShortArrayList fromShorts(final short... shorts) {
		return new ShortArrayList(shorts);
	}
}
