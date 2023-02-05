package com.unrealdinnerbone.specfeatures.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.chat.ChatListener;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import com.unrealdinnerbone.specfeatures.ChatFilter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Mutable
	@Shadow @Final private List<ChatListener> chatListeners;

	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(CallbackInfo info) {
		List<ChatListener> newOnes = new ArrayList<>();
		newOnes.addAll(chatListeners);
		chatListeners = newOnes;
	}
	@Inject(method = "handleSystemChat", at = @At("HEAD"), cancellable = true)
	private void onSystemChat(ChatType chatType, Component component, CallbackInfo ci) {
		if(chatType.overlay().isEmpty()) {
			Component newComp = ChatFilter.filter(component);
			for (ChatListener chatListener : this.chatListeners) {
				chatListener.handle(chatType, newComp, null);
			}
			ci.cancel();
		}
	}

	@Inject(method = "handlePlayerChat", at = @At("HEAD"), cancellable = true)
	private void onPlayerChat(ChatType chatType, Component component, ChatSender chatSender, CallbackInfo ci) {
		if(chatType.overlay().isEmpty()) {
			Component newComp = ChatFilter.filter(component);
			for (ChatListener chatListener : this.chatListeners) {
				chatListener.handle(chatType, newComp, chatSender);
			}
			ci.cancel();
		}
	}
}
