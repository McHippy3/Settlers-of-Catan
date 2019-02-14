package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class City 
{
	Player p;
	VertexLink v;
	
	public City()
	{
		p = null;
		v = null;
	}
	
	public City(Player p)
	{
		this.p = p;
	}
	
	public City(Player i, VertexLink l)
	{
		p = i;
		v = l;
	}
}