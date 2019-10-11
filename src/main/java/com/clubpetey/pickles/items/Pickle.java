package com.clubpetey.pickles.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Pickle extends ItemFood {

	public static final int PICKLE_HEAL = 4;
    public static final float PICKLE_SAT = -2.0f;
	

	public Pickle(String name) {
		super(PICKLE_HEAL, PICKLE_SAT, false);
		setMaxStackSize(64);
		setAlwaysEdible();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.FOOD);
	}
	
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 10;
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World world, EntityPlayer player) {
		super.onFoodEaten(par1ItemStack, world, player);
		player.heal(2);
	}
	
	

}
