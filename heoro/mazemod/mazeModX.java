package heoro.mazemod;

import heoro.mazemod.blocks.BlockMazeWall;
import heoro.mazemod.blocks.BlockMazeWallEgg;
import heoro.mazemod.entities.EntityMazeSilverfish;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "HeoroMaze", name = "Heoro's Maze Mod", version = "1.0")
@NetworkMod(clientSideRequired = true,serverSideRequired = false)

public class MazeMod {
	
	@Instance("mazeMod")
	public static MazeMod instance;
	
	//Items
	public static Item mazeGun; //= new mazeMaker(2000).setUnlocalizedName("maze_maker");//.setTextureName("heoro:maze_maker");
	
	//Blocks
	public static Block blockMazeWall;
	public static Block blockMazeWallEgg;
	
	//Entities
	public static EntityLiving entityMazeSilverfish;
	
	@SidedProxy(clientSide = "heoro.mazemod.client.ClientProxy",serverSide = "heoro.mazemod.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {}
	
	@EventHandler
	public void load(FMLInitializationEvent event){
		
		//items
		mazeGun = new MazeMaker(5000);//.setTextureName("heoro:maze_maker");
		LanguageRegistry.addName(mazeGun,"The Maze Maker");
		
		//blocks
		blockMazeWall = new BlockMazeWall(3001, Material.rock);
		blockMazeWallEgg = new BlockMazeWallEgg(3002, Material.rock);
		//using your IDs because reasons
		
		GameRegistry.registerBlock(blockMazeWall,"Maze Wall Block");
		GameRegistry.registerBlock(blockMazeWallEgg,"Maze Wall Block (Egg)");

		MinecraftForge.setBlockHarvestLevel(blockMazeWall, "pickaxe", 3);

		LanguageRegistry.addName(blockMazeWall, "Maze Wall Block");
		LanguageRegistry.addName(blockMazeWallEgg, "Maze Wall Block (Egg)");
		LanguageRegistry.instance().addStringLocalization("entity.HeoroMaze.MazeSilverfish.name", "Maze Silverfish");
		
		//entities
        EntityRegistry.registerModEntity(EntityMazeSilverfish.class, "MazeSilverfish", 175, this, 70, 1, true);
        EntityList.IDtoClassMapping.put(175, EntityMazeSilverfish.class);
        EntityList.entityEggs.put(175, new EntityEggInfo(175, 0, 0xffffff));
		
        //renderers
		proxy.registerRenderers();		
	}
	
	//I don't think we even need to include the postInit or preInit if we aren't putting anything in them
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}

}
