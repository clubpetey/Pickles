package com.clubpetey.pickles.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ThrownPickle extends EntityThrowable {

	public ThrownPickle(World world) {
		super(world);
	}
	
	public ThrownPickle(World w, EntityPlayer p) {
		super(w, p);
	}

	public ThrownPickle(World w, double x, double y, double z) {
		super(w, x, y, z);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
		if (!this.world.isRemote) {
			
			double x = result.hitVec.x;
			double y = result.hitVec.y;
			double z = result.hitVec.z;
			
			Entity target = result.entityHit;
	        if (target != null && target instanceof EntityLivingBase) {
	        	EntityLivingBase mob = (EntityLivingBase)target;
	        	x = mob.posX;
	        	y = mob.posY;
	        	z = mob.posZ;
	        }
	
	        this.world.newExplosion(this, x, y, z, 0.0F, false, true);
	        this.world.playSound((EntityPlayer)null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.25F, 2.0f);
	        
			AxisAlignedBB box =  new AxisAlignedBB(x-1, y-1, z-1, x+1, y+1, z+1);
			List<EntityLivingBase> mobs = this.world.getEntitiesWithinAABB(EntityLivingBase.class, box) ;
			for (EntityLivingBase o : mobs) {
				float dmg = 1;
	            if (o instanceof EntityEnderman) dmg = 50;
	            if (o instanceof EntityZombie) dmg = 11;
	            if (o instanceof EntitySkeleton) dmg = 11;
	            if (o instanceof EntityCreeper) dmg = 50;  //dead
	
	            ((EntityLivingBase)o).attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), dmg);			
			}
	
	        this.setDead();
		}
		
	}



}
