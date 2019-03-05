package org.settlersofcatan;

import java.io.*;
import java.lang.*;
import static java.lang.System.*;
import java.util.*;
import org.settlersofcatan.*;
import java.util.ArrayList;

public class DevelopmentBank {
	static ArrayList<DevelopmentCard>knight = new ArrayList<DevelopmentCard>();
	static ArrayList<DevelopmentCard>monopoly = new ArrayList<DevelopmentCard>();
	static ArrayList<DevelopmentCard>yop = new ArrayList<DevelopmentCard>();
	static ArrayList<DevelopmentCard>roadBuild = new ArrayList<DevelopmentCard>();
	ArrayList<DevelopmentCard> chapel = new ArrayList<DevelopmentCard>();
	ArrayList<DevelopmentCard> library = new ArrayList<DevelopmentCard>();
	ArrayList<DevelopmentCard> palace = new ArrayList<DevelopmentCard>();
	ArrayList<DevelopmentCard> market = new ArrayList<DevelopmentCard>();
	ArrayList<DevelopmentCard> university = new ArrayList<DevelopmentCard>();
	
	public DevelopmentBank()
	{
		for(int i = 0;i<14;i++)
		{
			DevelopmentCard r = new DevelopmentCard("knight", "res/dev_cards/knight.png",i);
			knight.add(r);
		}
		
		for(int i = 0;i<2;i++)
		{
			DevelopmentCard r = new DevelopmentCard("monopoly", "res/dev_cards/monopoly.png",i+18);
			monopoly.add(r);
		}
		
		for(int i = 0;i<2;i++)
		{
			DevelopmentCard r = new DevelopmentCard("year of plenty", "res/dev_cards/yop.png",i+16);
			yop.add(r);
		}
		
		for(int i = 0;i<2;i++)
		{
			DevelopmentCard r = new DevelopmentCard("road building", "res/dev_cards/roadbuild.png",i+14);
			roadBuild.add(r);
		}
		
		for(int i = 0;i<1;i++)
		{
			DevelopmentCard r = new DevelopmentCard("chapel", "res/dev_cards/chapel.png",20);
			chapel.add(r);
		}
		for(int i = 0;i<1;i++)
		{
			DevelopmentCard r = new DevelopmentCard("library", "res/dev_cards/library.png",21);
			library.add(r);
		}
		for(int i = 0;i<1;i++)
		{
			DevelopmentCard r = new DevelopmentCard("market", "res/dev_cards/market.png",22);
			market.add(r);
		}
		for(int i = 0;i<1;i++)
		{
			DevelopmentCard r = new DevelopmentCard("palace", "res/dev_cards/palace.png",23);
			palace.add(r);
		}
		for(int i = 0;i<1;i++)
		{
			DevelopmentCard r = new DevelopmentCard("university", "res/dev_cards/university.png",24);
			university.add(r);
		}
	}
	public void giveDevCard(String type, ArrayList<DevelopmentCard> devList, int index)
	{
		if(type.equals("knight")) 
		{
				devList.add(new DevelopmentCard("knight", "res/dev_cards/knight.png", index));
		}
		if(type.equals("monopoly")) 
		{
				devList.add(new DevelopmentCard("monopoly", "res/dev_cards/monopoly.png", index));
		}
		if(type.equals("year of plenty")) 
		{
				devList.add(new DevelopmentCard("year of plenty", "res/dev_cards/yop.png", index));
		}
		if(type.equals("road building")) 
		{
				devList.add(new DevelopmentCard("road building", "res/dev_cards/roadbuild.png", index));
		}
		if(type.equals("chapel")) 
		{
				devList.add(new DevelopmentCard("chapel", "res/dev_cards/chapel.png", index));
		}
		if(type.equals("library")) 
		{
				devList.add(new DevelopmentCard("knight", "res/dev_cards/library.png", index));
		}
		if(type.equals("market")) 
		{
				devList.add(new DevelopmentCard("market", "res/dev_cards/market.png", index));
		}
		if(type.equals("palace")) 
		{
				devList.add(new DevelopmentCard("palace", "res/dev_cards/palace.png", index));
		}
		if(type.equals("university")) 
		{
				devList.add(new DevelopmentCard("university", "res/dev_cards/university.png", index));
		}
		
	}
	public static void takeDevCard(String type, ArrayList<DevelopmentCard> devList, int index)
	{
				if(type.equals("knight")) 
				{
					knight.add(devList.remove(index));		
				}
				else if(type.equals("road build")) 
				{
					roadBuild.add(devList.remove(index));
				}
				else if(type.equals("monopoly")) 
				{
					monopoly.add(devList.remove(index));
				}
				else if(type.equals("year of plenty")) 
				{
					yop.add(devList.remove(index));
				}
				
	}

}
