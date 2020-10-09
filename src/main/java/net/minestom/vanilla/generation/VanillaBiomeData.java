package net.minestom.vanilla.generation;

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

	public static ShortArrayList fromShorts(short... shorts) {
		return new ShortArrayList(shorts);
	}
}
