package net.minestom.vanilla.generation;

import lombok.Getter;
import lombok.Setter;

//https://thebookofshaders.com/12/
//https://www.ronja-tutorials.com/2018/09/29/voronoi-noise.html
public class VoronoiNoise {

	@Setter
	float amplifier = 143758.5453f;
	int seedX = 1;
	int seedY = 1;

	/**
	 * Return the noise value at the input location
	 *
	 * @param x x pos
	 * @param y y pos
	 * @return noise at the location. Range 2.0 - 0.0
	 */
	public Output getNoise(final float x, final float y) {
		final int xi = (int) Math.floor(x);
		final int yi = (int) Math.floor(y);
		final float xf = x - xi;
		final float yf = y - yi;
		float distance = Float.MAX_VALUE;
		Vec2 cell = new Vec2(0, 0);
		for (int xOffset = -1; xOffset <= 1; xOffset++)
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				final float pointX = xOffset + getRandomX(new Vec2(xi + xOffset, yi + yOffset)) - xf;
				final float pointY = yOffset + getRandomY(new Vec2(xi + xOffset, yi + yOffset)) - yf;
				final float tempDistance = pointX * pointX + pointY * pointY;
				if (tempDistance < distance) {
					distance = tempDistance;
					cell = new Vec2(xi + xOffset, yi + yOffset);
				}
			}
		return new Output(distance, cell);
	}

	//UTIL

	//21.563f and 23.2312f are just random numbers
	public float getRandomX(final Vec2 n) {
		return fract((float) (Math.sin(n.dot(new Vec2(seedX * 11.563f, seedY * 23.2312f))) * amplifier));
	}

	public float getRandomY(final Vec2 n) {
		return fract((float) (Math.sin(n.dot(new Vec2(seedX * 23.2312f, seedY * 11.563f))) * amplifier));
	}

	public float fract(final float n) {
		return (float) (n - Math.floor(n));
	}

	public void setSeed(final int seed) {
		seedX = seed & 0xAAAAAAAA;
		seedY = seed & 0x55555555;
	}

	public static class Vec2 {

		@Getter
		final float x, y;

		public Vec2(final float x, final float y) {
			this.x = x;
			this.y = y;
		}

		public float dot(final Vec2 other) {
			return x * other.x + y * other.y;
		}

	}

	public static class Output {

		@Getter
		final float distance;
		@Getter
		final Vec2 point;

		public Output(final float distance, final Vec2 point) {
			this.distance = distance;
			this.point = point;
		}

	}

}
