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
	
	/*public boolean hasCluster()
	{
		if(v[0].vertexID == 2 && v[1].vertexID == 4 && v[2].vertexID == 0)
		{
			return true;
		}
		
		if()
		{
			
		}
	}*/
}