package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.client.render.item.RenderItemCakeKnife;
import com.Girafi.culinarycultivation.init.ModItems;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders(){
        MinecraftForgeClient.registerItemRenderer(ModItems.cakeKnife, new RenderItemCakeKnife());

    }
}