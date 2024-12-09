package fr.kayrouge.popkorn.util;

public class MathUtil {

	public static double map(double value, double fromMin, double fromMax, double toMin, double toMax) {
		return (value - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
	}

}
