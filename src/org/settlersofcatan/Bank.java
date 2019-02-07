package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;
import java.util.ArrayList;

public class Bank 
{
	ArrayList<ResourceCard>woolList = new ArrayList<ResourceCard>();
	ArrayList<ResourceCard>brickList = new ArrayList<ResourceCard>();
	ArrayList<ResourceCard>grainList = new ArrayList<ResourceCard>();
	ArrayList<ResourceCard>woodList = new ArrayList<ResourceCard>();
	ArrayList<ResourceCard>oreList = new ArrayList<ResourceCard>();
	
	public Bank()
	{
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("sheep");
			woolList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("brick");
			woolList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("wheat");
			woolList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("wood");
			woolList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("stone");
			woolList.add(r);
		}
	}
	
	// Remove 0 & first indexes from the Bank's list for each respective resource
	public static void giveResource(String s, Player p, int q)
	{
		
	}
	
	public void takeResource()
	{
		
	}
	
}

