package heoro.mazemod.client.renderer.entity;

import heoro.mazemod.entities.EntityMazeSilverfish;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderMazeSilverfish extends RenderLiving
{
    private static final ResourceLocation mazesilverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
    //TODO: placeholder? are we going to reskin our silverfish?

    public RenderMazeSilverfish()
    {
        super(new ModelSilverfish(), 0.3F);
    }

    /**
     * Return the silverfish's maximum death rotation.
     */
    protected float getMazeSilverfishDeathRotation(EntityMazeSilverfish par1EntityMazeSilverfish)
    {
        return 180.0F;
    }

    /**
     * Renders the silverfish.
     */
    public void renderMazeSilverfish(EntityMazeSilverfish par1EntityMazeSilverfish, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityMazeSilverfish, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getMazeSilverfishTextures(EntityMazeSilverfish par1EntityMazeSilverfish)
    {
        return mazesilverfishTextures;
    }

    /**
     * Disallows the silverfish to render the renderPassModel.
     */
    protected int shouldMazeSilverfishRenderPass(EntityMazeSilverfish par1EntityMazeSilverfish, int par2, float par3)
    {
        return -1;
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMazeSilverfish((EntityMazeSilverfish)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    protected float getDeathMaxRotation(EntityLivingBase par1EntityLivingBase)
    {
        return this.getMazeSilverfishDeathRotation((EntityMazeSilverfish)par1EntityLivingBase);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldMazeSilverfishRenderPass((EntityMazeSilverfish)par1EntityLivingBase, par2, par3);
    }

    public void renderPlayer(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMazeSilverfish((EntityMazeSilverfish)par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getMazeSilverfishTextures((EntityMazeSilverfish)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMazeSilverfish((EntityMazeSilverfish)par1Entity, par2, par4, par6, par8, par9);
    }
}
