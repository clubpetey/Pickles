package com.clubpetey.pickles.items;

import com.clubpetey.pickles.Pickles;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Salt extends Item {

	public Salt(String name) {
		super();
		setMaxStackSize(64);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CreativeTabs.MATERIALS);		
	}

    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean flag = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
        BlockPos blockpos = flag ? pos : pos.offset(facing);
        ItemStack itemstack = player.getHeldItem(hand);

        if (player.canPlayerEdit(blockpos, facing, itemstack) &&
        world.mayPlace(world.getBlockState(blockpos).getBlock(), blockpos, false, facing, (Entity)null) && 
        Blocks.REDSTONE_WIRE.canPlaceBlockAt(world, blockpos) &&
        world.getBlockState(pos).getBlock() != Blocks.SNOW) {
            world.setBlockState(blockpos, Pickles.saltLine.getDefaultState());

            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, blockpos, itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }	
	
}
