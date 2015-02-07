package com.brisco.common.HTML.Mapping;

import com.brisco.common.Game.Direction;

public class DirectionMapper {
	public static String GetStringFromDirection(Direction direction) {
		if (direction == null) {
			return "";
		}
		switch (direction) {
		case North:
			return "North";
		case South:
			return "South";
		case West:
			return "West";
		case East:
			return "East";
		default:
			return "Unknown";
		}
	}
}
