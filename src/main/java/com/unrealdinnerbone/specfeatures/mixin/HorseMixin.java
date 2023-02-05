package com.unrealdinnerbone.specfeatures.mixin;

import com.unrealdinnerbone.specfeatures.api.IHorse;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Horse.class)
public abstract class HorseMixin extends AbstractHorse implements IHorse {
    protected HorseMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }
    @Override
    public double getTheJumpPower() {
        return getJumpPower();
    }
}
