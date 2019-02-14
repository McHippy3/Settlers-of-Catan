package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;

// The Line class is used to split up the board into 5 horizontal lines (3, 4, 5, 4, 3)

public class Line
{
	Tile[] tiles;
	
	public Line()
	{
		tiles = null;
	}
	
	public Line(Tile[] t)
	{
		tiles = t;
	}
	
	public Line(Line s)
	{
		tiles = s.tiles;
	}
}