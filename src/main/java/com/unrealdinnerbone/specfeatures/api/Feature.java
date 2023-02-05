package com.unrealdinnerbone.specfeatures.api;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.unrealdinnerbone.specfeatures.SpecFeatures;

import java.util.ArrayList;
import java.util.List;

public class Feature
{
    private final String name;
    private boolean enabled;
    private final KeyMapping keyBinding;

    public Feature(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key." + SpecFeatures.MOD_ID + "." + name + ".toggle",
                InputConstants.UNKNOWN.getValue(),
                "key.categories." + SpecFeatures.MOD_ID
        ));
    }

    public KeyMapping getKeyBinding() {
        return keyBinding;
    }

    public List<LiteralArgumentBuilder<FabricClientCommandSource>> getSubCommands() {
        List<LiteralArgumentBuilder<FabricClientCommandSource>> list = new ArrayList<>();
        list.add(ClientCommandManager.literal("toggle").executes(context -> {
            toggle();
            context.getSource().sendFeedback(Component.literal(isEnabled() ? "Enabled" : "Disabled" + " " + getName()));
            return 1;
        }));
        list.add(ClientCommandManager.literal("enable").executes(context -> {
            if(!isEnabled()) {
                toggle();
                context.getSource().sendFeedback(Component.literal("Enabled" + getName()));
            }
            context.getSource().sendFeedback(Component.literal(getName() + " Already Enabled"));
            return 1;
        }));
        list.add(ClientCommandManager.literal("disable").executes(context -> {
            if(isEnabled()) {
                toggle();
                context.getSource().sendFeedback(Component.literal("Disabled" + getName()));
            }
            context.getSource().sendFeedback(Component.literal(getName() + " Already Disabled"));
            return 1;
        }));
        return list;
    }

    public boolean toggle() {
        LocalPlayer player = Minecraft.getInstance().player;
        boolean newEnabled = enabled = !enabled;
        if(player != null) {
            player.displayClientMessage(Component.literal(getName() + (newEnabled ? " Enabled" : " Disabled")), true);
        }
        return newEnabled;

    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAllowed() {
        Player player = Minecraft.getInstance().player;
        return player != null && player.isSpectator() && enabled;
    }

    public String getName() {
        return name;
    }
}
