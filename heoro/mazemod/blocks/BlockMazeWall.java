package heoro.mazemod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockMazeWall extends Block
{
	public BlockMazeWall(int id, Material material)
	{
		super(id, Material.rock);
		setHardness(50F);
		setResistance(15F);
		setUnlocalizedName("mazeWallBlock");
		setCreativeTab(CreativeTabs.tabBlock);
		setTextureName("quartz_block_chiseled_top");
		//TODO: placeholder texture
	}
}
