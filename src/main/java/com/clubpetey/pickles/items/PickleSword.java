package com.clubpetey.pickles.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

public class PickleSword extends ItemSword {

	public PickleSword(String name, Item.ToolMaterial mat) {
		super(mat);
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
	
	@Override
	public int getItemEnchantability() {
		return 22; //gold level
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		DamageSource dmg = DamageSource.causePlayerDamage((EntityPlayer)player);
		if (target instanceof EntityCreeper) {
			target.attackEntityFrom(dmg, 50);
			stack.damageItem(4, player);
			return true;			
		}
		
		if (target instanceof EntityEnderman) {
			target.attackEntityFrom(dmg, 50);
			stack.damageItem(8, player);
			return true;			
		}
		
		if (target instanceof EntityZombie) {
			target.attackEntityFrom(dmg, 4);
			return true;			
		}
		
		if (target instanceof EntitySkeleton) {
			target.attackEntityFrom(dmg, 4);
			return true;			
		}
				
		stack.damageItem(1, player);
		return false;
	}
	

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack,	ItemStack par2ItemStack) {
		return false;
	}
	
}
