package com.Girafi.culinarycultivation.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityTesting extends EntityAnimal { //Started working on a crab, i guess?

    //EntityList
    //addMapping(EntityCow.class, "Cow", 92, 4470310, 10592673);

    public EntityTesting(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

}
