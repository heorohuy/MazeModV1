package heoro.mazemod;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class mazeSilverfishBlock extends BlockSilverfish{
	
	public mazeSilverfishBlock(int id) {
		super(id);
		this.setUnlocalizedName("mazesilverfishblock");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(10.0F).setResistance(10.0F).setStepSound(soundStoneFootstep);
		this.setTextureName("maze_silverfish_block");
	}
	
	public void registerIcons(IconRegister reg){
		if (blockID == mazeMod.mazeSilverfishBlock.blockID) {
			this.blockIcon = reg.registerIcon("maze_silverfish_block");
		}
	}

}
