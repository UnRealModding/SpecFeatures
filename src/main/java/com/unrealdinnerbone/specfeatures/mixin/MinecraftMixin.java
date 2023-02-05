package com.unrealdinnerbone.specfeatures.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(at = @At("RETURN"), method = "shouldEntityAppearGlowing", cancellable = true)
	private void init(Entity entity, CallbackInfoReturnable<Boolean> info) {
		if (Features.HIGHTLIGHT.isAllowed() && (Features.HIGHTLIGHT.getList().contains(entity.getType()))) {
			if(entity instanceof Player player) {
				if(PlayerUtil.getGameMode(player).isSurvival()) {
					info.setReturnValue(true);
				}
			}else {
				info.setReturnValue(true);
			}
		}
	}
}
