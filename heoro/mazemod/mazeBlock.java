package heoro.mazemod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class mazeBlock extends Block{

	public mazeBlock(int id) {
		super(id, Material.rock);
		this.setUnlocalizedName("mazeblock");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(50.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
		this.setTextureName("maze_block");
	}
	public void registerIcons(IconRegister reg){
		if (blockID == mazeMod.mazeBlock.blockID) {
			this.blockIcon = reg.registerIcon("maze_block");
		}
	}

}
