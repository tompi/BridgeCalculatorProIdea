package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Direction;

public class DirectionMapper {

	public static Direction GetDirectionFromString(char direction) {
		if (direction == 'W') {
			return Direction.West;
		} else if (direction == 'E') {
			return Direction.East;
		} else if (direction == 'S') {
			return Direction.South;
		} else {
			return Direction.North;
		}
	}

	public static String GetStringFromDirection(Direction direction) {
		if (direction == null)
			return "";
		switch (direction) {
		case West:
			return "W";
		case East:
			return "E";
		case South:
			return "S";
		default:
			return "N";
		}
	}
}
