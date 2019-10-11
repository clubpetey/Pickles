package com.clubpetey.pickles.blocks;

import java.util.Random;

import com.clubpetey.pickles.Pickles;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CucumberCrop extends BlockCrops {
	private static final int MAX_AGE = 6;
	public static final PropertyInteger CROP_AGE = PropertyInteger.create("age", 0, MAX_AGE);
	//private static final AxisAlignedBB[] CROP_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.35D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.40D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)};
	
    public CucumberCrop(String name) {
    	super();
		setUnlocalizedName(name);
		setRegistryName(name);		
	}
        
    @Override
	protected PropertyInteger getAgeProperty() {
		return CROP_AGE;
	}

    @Override
	public int getMaxAge(){
		return MAX_AGE;
	}
	
    @Override
	protected int getBonemealAgeIncrease(World worldIn)	{
		return MathHelper.getInt(worldIn.rand, 1, MAX_AGE);
	}
	
    @Override
    protected Item getSeed(){
        return Pickles.pickleSeeds;
    }

    @Override
    protected Item getCrop() {
        return Pickles.cucumber;
    }
    
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {CROP_AGE});
    }    

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
    	return 2 + random.nextInt(2) + fortune; 
    }    
}
