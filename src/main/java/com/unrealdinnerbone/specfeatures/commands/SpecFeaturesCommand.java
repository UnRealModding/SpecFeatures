package com.unrealdinnerbone.specfeatures.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import com.unrealdinnerbone.specfeatures.api.Feature;
import com.unrealdinnerbone.specfeatures.features.Features;

public class SpecFeaturesCommand {

    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
        {
            LiteralArgumentBuilder<FabricClientCommandSource> specfeatures = ClientCommandManager.literal("specfeatures");
            for (Feature feature : Features.FEATURES) {
                LiteralArgumentBuilder<FabricClientCommandSource> s = ClientCommandManager.literal(feature.getName());
                for (LiteralArgumentBuilder<FabricClientCommandSource> subCommand : feature.getSubCommands()) {
                    s = s.then(subCommand);
                }
                specfeatures = specfeatures.then(s);
            }
            dispatcher.register(specfeatures);
        });
    }
}
