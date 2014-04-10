/**
 * 
 */
package com.stargem.utils;

import java.util.Random;

/**
 * 
 *
 */
public class SimplexNoise {

	private final SimplexNoiseOctave[] octaves;
	private final double[] frequencys;
	private final double[] amplitudes;

	private final int largestFeature;
	private final double persistence;
	private final int seed;

	/**
	 * This creates octaves that give features of size between 1 and largestFeature.
	 * 
	 * The ratios with which the different frequencies are combined is determined by the persistence.
	 * Persistence is used to affect the appearance of the terrain, high persistance (towards 1) gives 
	 * rocky mountainous terrain. low persistance (towards 0) gives slowly varying flat terrain.
	 * 
	 * @param largestFeature octaves give features of size between 1 and largestFeature
	 * @param persistence Persistence is used to affect the appearance of the terrain, high persistance (towards 1) gives rocky mountainous terrain. low persistance (towards 0) gives slowly varying flat terrain.
	 * @param seed a random number seed
	 */
	public SimplexNoise(int largestFeature, double persistence, int seed) {
		this.largestFeature = largestFeature;
		this.persistence = persistence;
		this.seed = seed;

		//recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
		int numberOfOctaves = (int) Math.ceil(Math.log10(largestFeature) / Math.log10(2));

		octaves = new SimplexNoiseOctave[numberOfOctaves];
		frequencys = new double[numberOfOctaves];
		amplitudes = new double[numberOfOctaves];

		Random rnd = new Random(seed);

		for (int i = 0; i < numberOfOctaves; i++) {
			octaves[i] = new SimplexNoiseOctave(rnd.nextInt());
			frequencys[i] = Math.pow(2, i);
			amplitudes[i] = Math.pow(persistence, octaves.length - i);
		}
	}

	/**
	 * get the noise value at the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double getNoise(int x, int y) {
		double result = 0;

		for (int i = 0; i < octaves.length; i++) {
			//double frequency = Math.pow(2,i);
			//double amplitude = Math.pow(persistence,octaves.length-i);
			result = result + octaves[i].noise(x / frequencys[i], y / frequencys[i]) * amplitudes[i];
		}
		return result;
	}

	/**
	 * get the noise value at the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public double getNoise(float x, float y, float z) {
		double result = 0;

		for (int i = 0; i < octaves.length; i += 1) {
			double frequency = Math.pow(2, i);
			double amplitude = Math.pow(persistence, octaves.length - i);
			result = result + octaves[i].noise(x / frequency, y / frequency, z / frequency) * amplitude;
		}
		return result;
	}
}