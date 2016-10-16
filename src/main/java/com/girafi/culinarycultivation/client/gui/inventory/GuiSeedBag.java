package com.girafi.culinarycultivation.client.gui.inventory;

import com.girafi.culinarycultivation.inventory.ContainerSeedBag;
import com.girafi.culinarycultivation.inventory.SeedBagInventory;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSeedBag extends GuiContainer {
    private static final ResourceLocation SEED_BAG_GUI_TEXTURE = new ResourceLocation(Reference.MOD_ID, Paths.GUI + "seed_bag.png");
    private final IInventory playerInventory;
    private final SeedBagInventory seedBagInventory;

    public GuiSeedBag(InventoryPlayer playerInv, SeedBagInventory seedBagInv) {
        super(new ContainerSeedBag(playerInv, seedBagInv, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInv;
        this.seedBagInventory = seedBagInv;
        this.ySize = 133;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(this.seedBagInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(SEED_BAG_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}