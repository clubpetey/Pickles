package com.clubpetey.pickles;

import org.apache.logging.log4j.Logger;

import com.clubpetey.pickles.ai.AIBlocked;
import com.clubpetey.pickles.blocks.BasicOre;
import com.clubpetey.pickles.blocks.CucumberCrop;
import com.clubpetey.pickles.blocks.OreGenerator;
import com.clubpetey.pickles.blocks.SaltLine;
import com.clubpetey.pickles.entities.ThrownPickle;
import com.clubpetey.pickles.items.BasicItem;
import com.clubpetey.pickles.items.Cucumber;
import com.clubpetey.pickles.items.Pickle;
import com.clubpetey.pickles.items.PickleBomb;
import com.clubpetey.pickles.items.PickleSword;
import com.clubpetey.pickles.items.Salt;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;



@Mod(modid = Pickles.MODID, name = Pickles.NAME, version = Pickles.VERSION, acceptedMinecraftVersions = Pickles.ACCEPTED_MINECRAFT)
@EventBusSubscriber
public class Pickles {
    public static final String MODID = "pickles";
    public static final String NAME = "Pickles";
    public static final String VERSION = "1.0";
	public static final String ACCEPTED_MINECRAFT = "[1.12,1.12.2]";
	
	public static Logger logger;
	
    public static Block picklePlant;  
	public static Block saltOre;  
	public static Block saltLine;  
	
    public static Item cucumber;
    public static Item pickle;
    public static Item pickleBomb;
    public static Item pickleSword;
    public static Item salt;
    public static Item pickleSeeds;
    public static Item flour;
    
    
    //Materials	
    public static Item.ToolMaterial Pickle = EnumHelper.addToolMaterial("PICKLE", 0, 200, 2.0F, -3, 22);
    
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
			cucumber, pickle, pickleBomb,pickleSword,
			pickleSeeds, salt, flour
		);

		event.getRegistry().registerAll(
				new ItemBlock(picklePlant).setRegistryName(picklePlant.getRegistryName()),
				new ItemBlock(saltOre).setRegistryName(saltOre.getRegistryName())
		);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(picklePlant, saltOre, saltLine);
	}
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        ConfigHandler.init(event.getSuggestedConfigurationFile());
    	logger.info("Pickle POWER!");
    	
        picklePlant = new CucumberCrop(Reference.CUCUMBERPLANT_NAME);
    	saltOre = new BasicOre(Reference.SALTORE_NAME, Material.ROCK, salt, SoundType.SAND);
    	saltLine = new SaltLine(Reference.SALTLINE_NAME);
        
    	cucumber = new Cucumber(Reference.CUCUMBER_NAME);
        pickle = new Pickle(Reference.PICKLE_NAME);
        pickleBomb = new PickleBomb(Reference.PICKLEBOMB_NAME);
        pickleSword = new PickleSword(Reference.PICKLESWORD_NAME, Pickle);
        salt = new Salt(Reference.SALT_NAME);
        
        pickleSeeds = new ItemSeeds(picklePlant, Blocks.FARMLAND)
        		.setRegistryName(Reference.PICKLESEEDS_NAME)
        		.setUnlocalizedName(Reference.PICKLESEEDS_NAME);
    	
        flour = new BasicItem(Reference.FLOUR_NAME);
        
		EntityRegistry.registerModEntity(new ResourceLocation(Pickles.MODID+":"+Reference.PICKLE_ENTITY), ThrownPickle.class, Reference.PICKLE_ENTITY, 1, this, 80, 10, true);

        MinecraftForge.EVENT_BUS.register(this);
    }


	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerRenders(ModelRegistryEvent event) {
		registerRender(Item.getItemFromBlock(saltOre));
		registerRender(Item.getItemFromBlock(picklePlant));
		
		registerRender(cucumber);
		registerRender(pickle);
		registerRender(pickleBomb);
		registerRender(pickleSword);
		registerRender(pickleSeeds);
		registerRender(salt);
		registerRender(flour);
		
        RenderingRegistry.registerEntityRenderingHandler(ThrownPickle.class, manager -> new RenderSnowball<>(manager, Pickles.pickleBomb, Minecraft.getMinecraft().getRenderItem()));
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
	}
    
    
	@EventHandler
    public void load(FMLInitializationEvent event) {    	
    	MinecraftForge.addGrassSeed(new ItemStack(pickleSeeds), 5);    	
    	GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
        OreDictionary.registerOre("itemSalt", salt);
        OreDictionary.registerOre("dustSalt", salt);
        OreDictionary.registerOre("foodSalt", salt);

        OreDictionary.registerOre("blockSalt", saltOre);
}

	@SubscribeEvent
    public void monsterSpawned(EntityJoinWorldEvent event) {
    	Entity mob = event.getEntity();
		if (!(mob instanceof EntityLivingBase)) return;
		EntityLivingBase e = (EntityLivingBase)mob; 
		if (e.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD || e instanceof EntityCreeper) {
			((EntityLiving)mob).tasks.addTask(0, new AIBlocked((EntityCreature)mob, Pickles.saltLine, 1.5f));
		}
	}

    
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
         
    }
    
    
    
	
}
