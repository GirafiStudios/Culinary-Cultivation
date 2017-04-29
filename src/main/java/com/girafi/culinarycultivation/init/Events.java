package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.event.FishingLootEvent;
import com.girafi.culinarycultivation.event.MobDropEvent;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import static com.girafi.culinarycultivation.event.InteractEvents.*;
import static com.girafi.culinarycultivation.event.ItemCraftingEvent.AchievementTriggerEvent;
import static com.girafi.culinarycultivation.event.ItemCraftingEvent.CraftedEvent;
import static com.girafi.culinarycultivation.util.ConfigurationHandler.*;

public class Events {
    public static void init() {
        register(new VanillaCrops(), canRightClickHarvestVanillaCrops);
        register(new MobDropEvent().setChildDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.VEAL.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.VEAL.getMetadata()), 0, 3), shouldBabyCowDropVeal);
        register(new MobDropEvent().setChildDrop(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, MeatType.LAMB.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.LAMB.getMetadata()), 1, 2), shouldBabySheepDropLamb);
        register(new MobDropEvent().setDrop(EntityChicken.class, new ItemStack(ModItems.MEAT, 1, MeatType.CHICKEN_WING.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.CHICKEN_WING.getMetadata()), 0, 2), shouldChickensDropChickenWings);
        register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.RIBS_BEEF.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.RIBS.getMetadata()), 0, 3), shouldCowsDropBeefRibs);
        register(new MobDropEvent().setDrop(EntityCow.class, new ItemStack(ModItems.MEAT, 1, MeatType.ROAST.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.ROAST.getMetadata()), 0, 1), shouldCowsDropRoast);
        register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.MEAT, 1, MeatType.HAM.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.HAM.getMetadata()), 0, 1), shouldPigsDropHam);
        register(new MobDropEvent().setDrop(EntityPig.class, new ItemStack(ModItems.MEAT, 1, MeatType.RIBS.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.RIBS.getMetadata()), 1, 2), shouldPigsDropPorkRibs);
        register(new MobDropEvent().setDrop(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, MeatType.LEG_SHEEP.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, MeatType.LEG_SHEEP.getMetadata()), 0, 2), shouldSheepDropLegOfSheep);
        register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.MEAT, 1, MeatType.SQUID_MANTLE.getMetadata()), 1, 2), shouldSquidDropSquidMantle);
        register(new MobDropEvent().setWaterDrop(EntitySquid.class, new ItemStack(ModItems.MEAT, 1, MeatType.SQUID_TENTACLE.getMetadata()), 0, 1), shouldSquidDropSquidTentacle);
        register(new MobDropEvent().setFullDrop(EntityCow.class, true, new ItemStack(ModItems.CALF_BELLY), null, false, ModItems.MEAT_CLEAVER, -1, 0, 1));
        register(new AchievementTriggerEvent());
        register(new CakeKnifeInteractionEvent());
        register(new CaneKnife());
        register(new CheeseInteractionEvent());
        register(new CraftedEvent());
        register(new DebugItemEvent());
        register(new FishingLootEvent());
        register(new StorageJarMilkFill());
        register(new StorageJarMilkFill());
        register(ModItems.DEBUG_ITEM);
        register(ModItems.SEED_BAG);
    }

    public static void register(Object eventClass, boolean b) {
        if (b) {
            register(eventClass);
        }
    }

    public static void register(Object eventClass) {
        MinecraftForge.EVENT_BUS.register(eventClass);
    }
}