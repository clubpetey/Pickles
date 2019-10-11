package com.clubpetey.pickles.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.clubpetey.pickles.Pickles;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SaltLine extends BlockRedstoneWire {
	
    public SaltLine(String name)  {
        super();
		setUnlocalizedName(name);
		setRegistryName(name);	
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Pickles.salt);
    }
    
    @Override
    public boolean canProvidePower(IBlockState state) {
    	return state.getBlock() == Pickles.saltLine;
    }
    
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
    	return state.getBlock() == Pickles.saltLine;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Pickles.salt;
    }    
    
    
    
}
