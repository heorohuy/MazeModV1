package heoro.mazemod;

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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "HeoroMaze", name = "Heoro's Maze Mod", version = "1.0")
@NetworkMod(clientSideRequired = true,serverSideRequired = false)

public class mazeMod {
	
	@Instance("mazeMod")
	public static mazeMod instance;
	
	//Items
	public static Item mazeGun; //= new mazeMaker(2000).setUnlocalizedName("maze_maker");//.setTextureName("heoro:maze_maker");
	
	//Blocks
	public static Block mazeBlock;
	public static BlockSilverfish mazeSilverfishBlock;
	
	@SidedProxy(clientSide = "heoro.mazemod.client.ClientProxy",serverSide = "heoro.mazemod.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event){
		
		//items
		mazeGun = new mazeMaker(5000);//.setTextureName("heoro:maze_maker");
		LanguageRegistry.addName(mazeGun,"The Maze Maker");
		
		//blocks
		mazeBlock = new mazeBlock(3001);
		GameRegistry.registerBlock(mazeBlock,"Maze Wall");
		LanguageRegistry.addName(mazeBlock, "Maze Wall");
		mazeSilverfishBlock = new mazeSilverfishBlock(3002);
		GameRegistry.registerBlock(mazeSilverfishBlock,"Maze Trap Wall");
		LanguageRegistry.addName(mazeSilverfishBlock, "Maze Trap Wall");
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}

}
