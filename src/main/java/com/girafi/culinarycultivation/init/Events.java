package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.event.FishingLootEvent;
import com.girafi.culinarycultivation.event.MobDropEvent;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.girafi.culinarycultivation.item.equipment.tool.ItemDebugItem;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import static com.girafi.culinarycultivation.event.InteractEvents.*;
import static com.girafi.culinarycultivation.event.ItemCraftingEvent.AchievementTriggerEvent;
import static com.girafi.culinarycultivation.event.ItemCraftingEvent.CraftedEvent;
import static com.girafi.culinarycultivation.util.ConfigurationHandler.*;

public class Events {
    public static void init() {
        if (CanRightClickHarvestVanillaCrops) { register(new VanillaCrops()); }
        if (ShouldBabyCowDropVeal) { register(new MobDropEvent().setChildDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.VEAL.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.VEAL.getMetaData()), 0, 3)); }
        if (ShouldBabySheepDropLamb) { register(new MobDropEvent().setChildDrop(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, MeatType.LAMB.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.LAMB.getMetaData()), 1, 2)); }
        if (ShouldChickensDropChickenWings) { register(new MobDropEvent().setDrop(EntityChicken.class, new ItemStack(ModItems.MEAT, 1, MeatType.CHICKEN_WING.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.CHICKEN_WING.getMetaData()), 0, 2)); }
        if (ShouldCowsDropBeefRibs) { register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.RIBS_BEEF.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.RIBS.getMetaData()), 0, 3)); }
        if (ShouldCowsDropRoast) { register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.ROAST.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.ROAST.getMetaData()), 0, 1)); }
        if (ShouldPigsDropHam) { register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.MEAT, 1, MeatType.HAM.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.HAM.getMetaData()), 0, 1)); }
        if (ShouldPigsDropPorkRibs) { register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.MEAT, 1, MeatType.RIBS.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.RIBS.getMetaData()), 1, 2)); }
        if (ShouldSheepDropLegOfSheep) { register(new MobDropEvent().setDrop(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, MeatType.LEG_SHEEP.getMetaData()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.LEG_SHEEP.getMetaData()), 0, 2)); }
        if (ShouldSquidDropSquidMantle) { register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.MEAT, 1, MeatType.SQUID_MANTLE.getMetaData()), 1, 2)); }
        if (ShouldSquidDropSquidTentacle) { register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.MEAT, 1, MeatType.SQUID_TENTACLE.getMetaData()), 0, 1)); }
        register(new MobDropEvent().setFullDrop(EntityCow.class, true, new ItemStack(ModItems.CALF_BELLY), null, false, ModItems.MEAT_CLEAVER, -1, 0, 1));
        register(new AchievementTriggerEvent());
        register(new CraftedEvent());
        register(new CakeKnifeInteractionEvent());
        register(new CheeseInteractionEvent());
        register(new CaneKnife());
        register(new CauldronTransformation());
        register(new DebugItemEvent());
        register(new StorageJarMilkFill());
        register(new FishingLootEvent());
        register(new ItemDebugItem());
    }

    public static void register(Object eventClass) {
        MinecraftForge.EVENT_BUS.register(eventClass);
    }
}