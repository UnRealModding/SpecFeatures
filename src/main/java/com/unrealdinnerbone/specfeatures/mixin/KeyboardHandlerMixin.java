package com.unrealdinnerbone.specfeatures.mixin;

import com.google.common.base.MoreObjects;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.PlayerUtil;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {

    @Shadow protected abstract void debugFeedbackTranslated(String string, Object... objects);

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "handleDebugKeys", at = @At(value = "HEAD"), cancellable = true)
    public void onMesssage(int i, CallbackInfoReturnable<Boolean> cir) {
        if(i == 78) {
            if (!PlayerUtil.getSelf().isSpectator()) {
                this.minecraft.player.command("gamemode spectator");
            } else {
                this.minecraft.player.command("gamemode " + MoreObjects.firstNonNull(this.minecraft.gameMode.getPreviousPlayerMode(), GameType.CREATIVE).getName());
            }
            cir.setReturnValue(true);
        }
    }
}
