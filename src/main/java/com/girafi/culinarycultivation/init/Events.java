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
        if (canRightClickHarvestVanillaCrops) { register(new VanillaCrops()); }
        if (shouldBabyCowDropVeal) { register(new MobDropEvent().setChildDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.VEAL.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.VEAL.getMetadata()), 0, 3)); }
        if (shouldBabySheepDropLamb) { register(new MobDropEvent().setChildDrop(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, MeatType.LAMB.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.LAMB.getMetadata()), 1, 2)); }
        if (shouldChickensDropChickenWings) { register(new MobDropEvent().setDrop(EntityChicken.class, new ItemStack(ModItems.MEAT, 1, MeatType.CHICKEN_WING.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.CHICKEN_WING.getMetadata()), 0, 2)); }
        if (shouldCowsDropBeefRibs) { register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.RIBS_BEEF.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.RIBS.getMetadata()), 0, 3)); }
        if (shouldCowsDropRoast) { register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.ROAST.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.ROAST.getMetadata()), 0, 1)); }
        if (shouldPigsDropHam) { register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.MEAT, 1, MeatType.HAM.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.HAM.getMetadata()), 0, 1)); }
        if (shouldPigsDropPorkRibs) { register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.MEAT, 1, MeatType.RIBS.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.RIBS.getMetadata()), 1, 2)); }
        if (shouldSheepDropLegOfSheep) { register(new MobDropEvent().setDrop(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, MeatType.LEG_SHEEP.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.LEG_SHEEP.getMetadata()), 0, 2)); }
        if (shouldSquidDropSquidMantle) { register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.MEAT, 1, MeatType.SQUID_MANTLE.getMetadata()), 1, 2)); }
        if (shouldSquidDropSquidTentacle) { register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.MEAT, 1, MeatType.SQUID_TENTACLE.getMetadata()), 0, 1)); }
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