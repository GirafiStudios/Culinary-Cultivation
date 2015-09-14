package thaumcraft.api.internal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;

public class DummyInternalMethodHandler implements IInternalMethodHandler {

	@Override
	public boolean isResearchComplete(String username, String researchkey) {
		return false;
	}	

	@Override
	public AspectList getObjectAspects(ItemStack is) {
		return null;
	}

	@Override
	public AspectList getBonusObjectTags(ItemStack is, AspectList ot) {
		return null;
	}

	@Override
	public AspectList generateTags(Item item, int meta) {
		return null;
	}

	@Override
	public boolean consumeVisFromWand(ItemStack wand, EntityPlayer player,
			AspectList cost, boolean doit, boolean crafting) {
		return false;
	}

	@Override
	public boolean consumeVisFromWandCrafting(ItemStack wand,
			EntityPlayer player, AspectList cost, boolean doit) {
		return false;
	}

	@Override
	public boolean consumeVisFromInventory(EntityPlayer player, AspectList cost) {
		return false;
	}

	@Override
	public void addWarpToPlayer(EntityPlayer player, int amount, boolean temporary) {
	}

	@Override
	public void addStickyWarpToPlayer(EntityPlayer player, int amount) {
	}

	@Override
	public void markRunicDirty(Entity entity) { 
		
	}
	
}
