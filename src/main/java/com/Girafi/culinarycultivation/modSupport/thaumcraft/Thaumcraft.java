package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.reference.SupportedModIDs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.wands.WandTriggerRegistry;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;

public class Thaumcraft implements IModSupport {
    @Override
    public void preInit() {
        NetworkHandler.instance.registerPacket(PacketFXBlockSparkle.class, Side.CLIENT);
    }

    @Override
    public void init() {
        Aspects.init();

        //TODO Add crop support for golem harvesting
        //FMLInterModComms.sendMessage(SupportedModIDs.TC, "harvestStandardCrop", new ItemStack(ModBlocks.beetroots, 1, 7));

        ThaumcraftApi.addSmeltingBonus("fish", new ItemStack(ItemsTC.chunks, 0, 3));
        ThaumcraftApi.addSmeltingBonus(new ItemStack(ModItems.fish, 0, 7), new ItemStack(ItemsTC.chunks, 0, 3));
    }

    @Override
    public void postInit() {
        BlockModCauldron cauldron = (BlockModCauldron) ModBlocks.cauldron;
        for (int i = 0; i < cauldron.LEVEL.getAllowedValues().size() - 1; i++) {
            WandTriggerRegistry.registerWandBlockTrigger(new CrucibleSupport(), 1, cauldron.getStateFromMeta(i), Reference.MOD_ID);
        }
    }

    @Override
    public void clientSide() {
    }
}