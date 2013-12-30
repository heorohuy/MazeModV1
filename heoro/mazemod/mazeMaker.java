package heoro.mazemod;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MazeMaker extends Item{

	public MazeMaker(int id) {
		super(id);
		this.setUnlocalizedName("maze_maker");
		this.setCreativeTab(CreativeTabs.tabTools); 
	}


	public void registerIcons(IconRegister reg){
		if (itemID == MazeMod.mazeGun.itemID) {
			this.itemIcon = reg.registerIcon("maze_maker");
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){
		MyMaze maze = new MyMaze(10,world,x,25,z);//Set the Y value to 25 when generating the world.
        return false;
    }
}
