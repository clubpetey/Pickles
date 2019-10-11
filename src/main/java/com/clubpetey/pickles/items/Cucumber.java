package com.clubpetey.pickles.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class Cucumber extends ItemFood {
	
    public static final int CUCUMBER_HEAL = 1;
    public static final float CUCUMBER_SAT = 6.0f;
	
	public Cucumber(String name) {
		super(CUCUMBER_HEAL, CUCUMBER_SAT, false);
		setMaxStackSize(64);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.FOOD);
	}
	
	

}
