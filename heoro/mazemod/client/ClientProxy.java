package heoro.mazemod.client;


import heoro.mazemod.CommonProxy;
import heoro.mazemod.client.renderer.entity.RenderMazeSilverfish;
import heoro.mazemod.entities.EntityMazeSilverfish;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void registerRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(EntityMazeSilverfish.class, new RenderMazeSilverfish());
	}
}
