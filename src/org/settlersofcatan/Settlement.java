package org.settlersofcatan;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.*;
import java.awt.Color;
import java.io.*;
import java.lang.Math;

public class Settlement 
{
	Player p;
	VertexLink v;
	
	public Settlement()
	{
		p = null;
		v = null;
	}
	
	public Settlement(Player p)
	{
		this.p = p;
	}
	
	public Settlement(Player i, VertexLink l)
	{
		p = i;
		v = l;
	}
}