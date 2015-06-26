package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModItems;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy {
    //private final List<SourceBlockRenderHandler> renderBlocks = new ArrayList<SourceBlockRenderHandler>();

//    @Override
//    public void registerRenders(){
//        MinecraftForgeClient.registerItemRenderer(ModItems.cakeKnife, new RenderItemCakeKnife());
//
//        RENDER_VALUE = RenderingRegistry.getNextAvailableRenderId();
//        renderBlocks.add(new RenderCauldron());
//
//        for(SourceBlockRenderHandler renderer : renderBlocks) {
//            RenderingRegistry.registerBlockHandler(renderer);
//        }
//    }
//
//    @Override
//    public int getRenderIdForRenderer(Class c){
//        for(SourceBlockRenderHandler renderer : renderBlocks) {
//            if(renderer.getClass() == c) return renderer.getRenderId();
//        }
//        throw new IllegalArgumentException("Renderer " + c.getCanonicalName() + " is not registered");
//    }
}