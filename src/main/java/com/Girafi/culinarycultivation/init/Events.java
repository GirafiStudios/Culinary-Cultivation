package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.event.MobDropEvent;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.event.InteractEvents.*;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.AchievementTriggerEvent;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.CraftedEvent;
import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.*;

public class Events {
    public static void init() {
        if (CanRightClickHarvestVanillaCrops) { register(new VanillaCrops()); }
        if (ShouldBabyCowDropVeal) { register(new MobDropEvent().setChildDrop(EntityCow.class, new ItemStack(ModItems.meat, 1, MeatType.VEAL.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.VEAL.getMetaData()), 0, 3)); }
        if (ShouldBabySheepDropLamb) { register(new MobDropEvent().setChildDrop(EntitySheep.class, new ItemStack(ModItems.meat, 1, MeatType.LAMB.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.LAMB.getMetaData()), 1, 2)); }
        if (ShouldChickensDropChickenWings) { register(new MobDropEvent().setDrop(EntityChicken.class, new ItemStack(ModItems.meat, 1, MeatType.CHICKENWING.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.CHICKENWING.getMetaData()), 0, 2)); }
        if (ShouldCowsDropBeefRibs) { register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.meat, 1, MeatType.RIBSBEEF.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.RIBS.getMetaData()), 0, 3)); }
        if (ShouldCowsDropRoast) { register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.meat, 1, MeatType.ROAST.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.ROAST.getMetaData()), 0, 1)); }
        if (ShouldPigsDropHam) { register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.meat, 1, MeatType.HAM.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.HAM.getMetaData()), 0, 1)); }
        if (ShouldPigsDropPorkRibs) { register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.meat, 1, MeatType.RIBS.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.RIBS.getMetaData()), 1, 2)); }
        if (ShouldSheepDropLegOfSheep) { register(new MobDropEvent().setDrop(EntitySheep.class, new ItemStack(ModItems.meat, 1, MeatType.LEGSHEEP.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.LEGSHEEP.getMetaData()), 0, 2)); }
        if (ShouldSquidDropSquidMantle) { register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.meat, 1, MeatType.SQUIDMANTLE.getMetaData()), 1, 2)); }
        if (ShouldSquidDropSquidTentacle) { register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.meat, 1, MeatType.SQUIDTENTACLE.getMetaData()), 0, 1)); }
        register(new MobDropEvent().setFullDrop(EntityCow.class, true, new ItemStack(ModItems.calfBelly), null, false, ModItems.meatCleaver, -1, 0, 1));
        register(new AchievementTriggerEvent());
        register(new CraftedEvent());
        register(new CakeInteractionEvent()); //TODO
        register(new CheeseInteractionEvent()); //TODO
        register(new CaneKnife());
        register(new CauldronTransformation());
        register(new DebugItemEvent());
        register(new StorageJarMilkFill());
    }

    public static void register(Object eventClass) {
        MinecraftForge.EVENT_BUS.register(eventClass);
    }
}