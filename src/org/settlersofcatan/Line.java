package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;

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