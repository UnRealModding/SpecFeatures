package com.unrealdinnerbone.specfeatures.commands;

import com.unrealdinnerbone.specfeatures.SpecFeatures;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class NarrationCommand {
    public static void init() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(ClientCommandManager.literal("narrator")
                        .then(ClientCommandManager.literal("clear").executes(context -> {
                            SpecFeatures.SPEC_NARRATOR.clear();
                            return 1;
                        }))));
    }

}
