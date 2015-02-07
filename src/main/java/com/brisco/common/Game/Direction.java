package com.brisco.common.Game;

public enum Direction {
	North,
	South,
	East,
	West;
	
	public boolean IsNorthSouth()
	{
		return (this == North || this == South);
	}
}
