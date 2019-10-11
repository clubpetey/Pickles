package com.clubpetey.pickles.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BasicOre extends Block {
	Item toDrop;
	int minDropAmount = 1;
	int maxDropAmount = 0;
	
	public BasicOre(String name) {
		this(name, Material.ROCK, null, 1, 4, SoundType.STONE);
	}

	public BasicOre(String name, SoundType sound) {
		this(name, Material.ROCK, null, 1, 4, sound);
	}

	public BasicOre(String name, Material material, Item toDrop, SoundType sound) {
		this(name, material, toDrop, 1, 1, sound);
	}
	
	public BasicOre(String name, Material material, Item toDrop, int dropAmount) {
		this(name, material, toDrop, dropAmount, dropAmount, SoundType.STONE);
	}

	public BasicOre(String name, Material material, Item toDrop, int minDropAmount, int maxDropAmount, SoundType sound) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		setHardness(0.5F);
		setResistance(5.0f);	
		this.blockSoundType = sound;
		this.toDrop = toDrop;
		this.minDropAmount = minDropAmount;
		this.maxDropAmount = maxDropAmount;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return toDrop == null ? Item.getItemFromBlock(this) : toDrop;
	}
	
	@Override
	public int quantityDropped(Random random) {
	    if(this.minDropAmount>this.maxDropAmount) {
	        int i = this.minDropAmount;
	        this.minDropAmount=this.maxDropAmount;
	        this.maxDropAmount=i;
	    }
	    return this.minDropAmount + random.nextInt(this.maxDropAmount - this.minDropAmount + 1);
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)	{
	    if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(this.getDefaultState(), random, fortune)) {
	        int i = random.nextInt(fortune + 2) - 1;
	        if (i < 0) i = 0;
	        return this.quantityDropped(random) * (i + 1);
	    } else {
	       return this.quantityDropped(random);
	    }
	}	
}
