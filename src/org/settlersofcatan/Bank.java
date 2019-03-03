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
			ResourceCard r = new ResourceCard("wool");
			woolList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("brick");
			brickList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("grain");
			grainList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("wood");
			woodList.add(r);
		}
		
		for(int i = 0;i<30;i++)
		{
			ResourceCard r = new ResourceCard("ore");
			oreList.add(r);
		}
	}
	
	// Remove 0 & first indexes from the Bank's list for each respective resource
	public void giveResource(String type, Player p, int quantity)
	{
		for(int i = 0; i < quantity; i++) {
			if(type.equals("brick")) 
			{
				if(!brickList.isEmpty()) 
				{
					p.resList.add(brickList.remove(0));
				}
			}
			else if(type.equals("grain")) 
			{
				if(!grainList.isEmpty()) 
				{
					p.resList.add(grainList.remove(0));
				}
			}
			else if(type.equals("ore")) 
			{
				if(!oreList.isEmpty()) 
				{
					p.resList.add(oreList.remove(0));
				}
			}
			else if(type.equals("wood")) 
			{
				if(!woodList.isEmpty()) 
				{
					p.resList.add(woodList.remove(0));
				}
			}
			else if(type.equals("wool")) 
			{
				if(!woolList.isEmpty()) 
				{
					p.resList.add(woolList.remove(0));
				}
			}
		}
	}
	
	public void takeResource()
	{
		
	}
	
}

