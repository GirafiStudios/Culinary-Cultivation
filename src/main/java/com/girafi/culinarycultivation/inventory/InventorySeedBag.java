package com.girafi.culinarycultivation.inventory;

import com.girafi.culinarycultivation.util.reference.Paths;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.text.translation.I18n;

public class InventorySeedBag extends InventoryBasic {

    public InventorySeedBag() {
        super(I18n.translateToLocal(Paths.MOD_ASSETS + "container.seed_bag"), true, 1);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1280;
    }
}