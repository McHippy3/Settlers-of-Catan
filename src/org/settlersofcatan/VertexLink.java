package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;

public class VertexLink 
{
	Vertex[] v;
	
	public VertexLink()
	{
		v = null;
	}
	
	
	public VertexLink(Vertex[] v1)
	{
		v = v1;
	}
	
	public String toString(Vertex v)
	{
		return v.tileID+", "+v.vertexID;
	}
}