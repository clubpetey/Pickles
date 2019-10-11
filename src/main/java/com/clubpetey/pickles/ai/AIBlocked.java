package com.clubpetey.pickles.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AIBlocked extends EntityAITarget {

	private EntityCreature creature;
	private Block block;
	private float speed;
	private long delay;
	private Vec3d runTo;
	
    public AIBlocked(EntityCreature creature, Block block, float speed) {
    	super(creature, true, false);
        this.creature = creature;
        this.block = block;
        this.speed = speed;
        this.setMutexBits(3);
    }
    
	public static Vec3d findRandomBlockNear(World w, Vec3d dir, int i) {
		int x = MathHelper.floor(dir.x);
		int y = MathHelper.floor(dir.y);
		int z = MathHelper.floor(dir.z);
		int xx = w.rand.nextInt(i) - (i/2);
		int zz = w.rand.nextInt(i) - (i/2);
		int yy = 0;
		
		while (w.isAirBlock(new BlockPos(x+xx, y+yy, z+xx))) yy--;
		//while (w.isAirBlock(x+xx, y+yy+1, z+xx)) yy++;
		
		return new Vec3d(x+xx, y+yy, z+zz);
	}
	
	@Override
	public boolean shouldExecute() {
		long now = creature.world.getWorldTime();		
		if (now < delay) return false;

		//if (flee) return false;
		
		int x = MathHelper.floor(creature.posX);
		int y = MathHelper.floor(creature.posY);
		int z = MathHelper.floor(creature.posZ);
		
		BlockPos bp = new BlockPos(x, y, z);
		return  (creature.world.getBlockState(bp).getBlock() == block);
	}
	
	
	
	
	@Override
	public boolean shouldContinueExecuting() {		
		if (creature.world.getWorldTime() < delay) 
			return true;
		return !creature.getNavigator().noPath();
	}
	
	@Override
	public void updateTask() {
		creature.setAttackTarget(null);
		creature.getNavigator().tryMoveToXYZ(runTo.x, runTo.y, runTo.z, speed);
	}
	
	@Override
	public void resetTask() {
	}
	
	private static final int SCALE = 200;
	
	@Override
	public void startExecuting() {
		World w = creature.world;
		
		double x = creature.posX;
		double y = creature.posY;
		double z = creature.posZ;
		
		
		int xx = MathHelper.floor(x);
		int yy = MathHelper.floor(y);
		int zz = MathHelper.floor(z);
		
		boolean lineX = (w.getBlockState(new BlockPos(xx-1,yy, zz)).getBlock() == block) || 
				(w.getBlockState(new BlockPos(xx+1, yy, zz)).getBlock() == block);
		boolean lineZ = (w.getBlockState(new BlockPos(xx, yy, zz-1)).getBlock() == block) || 
				(w.getBlockState(new BlockPos(xx, yy, zz+1)).getBlock() == block);
		
		double max = creature.getNavigator().getPathSearchRange()-4;
		double dx = Math.min(Math.abs(creature.motionX)*SCALE, max);
		double dz = Math.min(Math.abs(creature.motionZ)*SCALE, max);
		if (creature.motionX < 0) dx = -dx;
		if (creature.motionZ < 0) dz = -dz;
		
		if (lineX) dz = -dz;
		if (lineZ) dx= -dx;
		
		Vec3d dir = new Vec3d(x+dx, y, z+dz);
		delay = 0;
		creature.getNavigator().clearPath();
		do {
			Vec3d loc = findRandomBlockNear(w, dir, 6);
			runTo = loc;
		} while (!creature.getNavigator().tryMoveToXYZ(runTo.x, runTo.y, runTo.z, speed) && delay++ < 10);
		delay = w.getWorldTime() + 60;
	}
	
	@Override
	public boolean isInterruptible() {
		return false;
	}
	
	
}
