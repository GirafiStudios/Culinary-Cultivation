package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.event.MobDropEvent;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.event.CowDropsEvent.*;
import static com.Girafi.culinarycultivation.event.InteractEvents.*;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.AchievementTriggerEvent;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.CraftedEvent;
import static com.Girafi.culinarycultivation.event.PigDropsEvent.PigHamDropsEvent;
import static com.Girafi.culinarycultivation.event.PigDropsEvent.PigRibsPorkDropsEvent;
import static com.Girafi.culinarycultivation.event.SheepDropsEvent.BabySheepLambDropsEvent;
import static com.Girafi.culinarycultivation.event.SheepDropsEvent.SheepLegOfSheepDropsEvent;
import static com.Girafi.culinarycultivation.event.SquidDropsEvent.SquidMantleDropsEvent;
import static com.Girafi.culinarycultivation.event.SquidDropsEvent.SquidTentacleDropsEvent;
import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.*;

public class Events {
    public static MobDropEvent veal;
    public static MobDropEvent chickenWings;

    public static void init() {
        if (CanRightClickHarvestVanillaCrops) { register(new VanillaCrops()); }
        if (ShouldBabyCowDropVeal) { register(veal = new MobDropEvent().setChildDrop(EntityCow.class, new ItemStack(ModItems.meat, 1, MeatType.VEAL.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.VEAL.getMetaData()), 1, 3)); }
        if (ShouldBabySheepDropLamb) { register( new BabySheepLambDropsEvent()); }
        if (ShouldChickensDropChickenWings) { register(chickenWings = new MobDropEvent().setDrop(EntityChicken.class, new ItemStack(ModItems.meat, 1, MeatType.CHICKENWING.getMetaData()), new ItemStack(ModItems.cooked_meat, 1, MeatType.CHICKENWING.getMetaData()), 0 , 2)); }
        if (ShouldCowsDropBeefRibs) { register(new CowRibsBeefDropsEvent()); }
        if (ShouldCowsDropRoast) { register(new CowRoastDropsEvent()); }
        if (ShouldPigsDropHam) { register(new PigHamDropsEvent()); }
        if (ShouldPigsDropPorkRibs) { register(new PigRibsPorkDropsEvent()); }
        if (ShouldSheepDropLegOfSheep) { register(new SheepLegOfSheepDropsEvent()); }
        if (ShouldSquidDropSquidMantle) { register(new SquidMantleDropsEvent()); }
        if (ShouldSquidDropSquidTentacle) { register(new SquidTentacleDropsEvent()); }
        register(new BabyCowCalfBellyDropsEvent());
        register(new AchievementTriggerEvent());
        register(new CraftedEvent());
        register(new CakeInteractionEvent());
        register(new CaneKnife());
        register(new CauldronTransformation());
        register(new CheeseInteractionEvent());
        register(new DebugItemEvent());
        register(new StorageJarMilkFill());
    }

    public static void register(Object eventClass) {
        MinecraftForge.EVENT_BUS.register(eventClass);
    }
}