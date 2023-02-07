package com.unrealdinnerbone.specfeatures.mixin;

import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.chat.ChatListener;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import com.unrealdinnerbone.specfeatures.chat.ChatEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Mutable
	@Shadow @Final private List<ChatListener> chatListeners;

	@Inject(method = "handleSystemChat", at = @At("HEAD"), cancellable = true)
	private void onSystemChat(ChatType chatType, Component component, CallbackInfo ci) {
		if(chatType.overlay().isEmpty()) {
			net.kyori.adventure.text.Component newComp = ChatEvent.onChat(component);
			for (ChatListener chatListener : this.chatListeners) {
				chatListener.handle(chatType, FabricClientAudiences.of().toNative(newComp), null);
			}
			ci.cancel();
		}
	}

	@Inject(method = "handlePlayerChat", at = @At("HEAD"), cancellable = true)
	private void onPlayerChat(ChatType chatType, Component component, ChatSender chatSender, CallbackInfo ci) {
		if(chatType.overlay().isEmpty()) {
			net.kyori.adventure.text.Component newComp = ChatEvent.onChat(component);
			for (ChatListener chatListener : this.chatListeners) {
				chatListener.handle(chatType, FabricClientAudiences.of().toNative(newComp), chatSender);
			}
			ci.cancel();
		}
	}
}
