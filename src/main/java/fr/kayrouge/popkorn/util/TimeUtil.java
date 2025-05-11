package fr.kayrouge.popkorn.util;

public class TimeUtil {


	public static int secondToTick(int second) {
		return second * 20;
	}

	public static int minuteToTick(int minute) {
		return secondToTick(minute*60);
	}

	public static int hourToTick(int hour) {
		return minuteToTick(hour*60);
	}

	public static int dayToTick(int day) {
		return hourToTick(day*24);
	}

	public static int minuteToTick(int minute, int second) {
		return secondToTick(minute*60)+secondToTick(second);
	}

	public static int hourToTick(int hour, int minute) {
		return minuteToTick(hour*60)+minuteToTick(minute);
	}
}
