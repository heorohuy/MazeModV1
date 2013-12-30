package heoro.mazemod.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import heoro.mazemod.entities.EntityMazeSilverfish;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import heoro.mazemod.mazeMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMazeWallEgg extends Block
{
    public BlockMazeWallEgg(int id, Material material)
    {
        super(id, Material.rock);
        setHardness(50F);
        setResistance(15F);
		setUnlocalizedName("mazeWallEggBlock");
        setCreativeTab(CreativeTabs.tabBlock);
		setTextureName("quartz_block_chiseled_top");
		//TODO: placeholder texture
    }

    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            EntityMazeSilverfish entitymazesilverfish = new EntityMazeSilverfish(par1World);
            entitymazesilverfish.setLocationAndAngles((double)par2 + 0.5D, (double)par3, (double)par4 + 0.5D, 0.0F, 0.0F);
            par1World.spawnEntityInWorld(entitymazesilverfish);
            entitymazesilverfish.spawnExplosionParticle();
        }

        super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }

    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}
