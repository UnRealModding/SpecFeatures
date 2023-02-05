package com.unrealdinnerbone.specfeatures.mixin;

import com.unrealdinnerbone.specfeatures.api.IHorse;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.HorseStatsConverter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Mixin(MultiPlayerGameMode.class)
public class PlayerMixin {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");


    @Inject(at = @At("HEAD"), method = "interact")
    public void interactMob(Player player, Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> ret) {
        if(entity instanceof Horse horse) {
            if(Features.HORSE_STATS.isAllowed()) {
                IHorse iHorse = (IHorse) horse;
                String jumpstrength = DECIMAL_FORMAT.format(HorseStatsConverter.jumpStrengthToJumpHeight(iHorse.getTheJumpPower()));
                String maxHealth = DECIMAL_FORMAT.format(horse.getMaxHealth());
                String speed = DECIMAL_FORMAT.format(HorseStatsConverter.genericSpeedToBlocPerSec(horse.getAttributes().getValue(Attributes.MOVEMENT_SPEED)));

                double jumpValue = new BigDecimal(jumpstrength.replace(',', '.')).doubleValue();
                double speedValue = new BigDecimal(speed.replace(',', '.')).doubleValue();
                double healthValue = new BigDecimal(maxHealth.replace(',', '.')).doubleValue();


                Component jumpValueComponent = Component.literal("Jump").withStyle(ChatFormatting.GREEN)
                        .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.valueOf(jumpValue)).withStyle(ChatFormatting.RED));

                Component maxHealthComponent = Component.literal("Max Health").withStyle(ChatFormatting.GREEN)
                        .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.valueOf(healthValue)).withStyle(ChatFormatting.RED));

                Component speedComponent = Component.literal("Speed").withStyle(ChatFormatting.GREEN)
                        .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.valueOf(speedValue)).withStyle(ChatFormatting.RED));

                Component groupedComponent = Component.literal("[SPEC]: ").withStyle(ChatFormatting.GOLD)
                        .append(jumpValueComponent)
                        .append(" ")
                        .append(maxHealthComponent)
                        .append(" ")
                        .append(speedComponent);

                player.displayClientMessage(groupedComponent, false);
            }
        }


    }

}
