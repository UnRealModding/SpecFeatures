package com.unrealdinnerbone.specfeatures.features;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.unrealdinnerbone.specfeatures.api.Feature;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RegistryListFeature<T> extends Feature {
    private final List<T> list;
    private final Registry<T> registry;
    private final SuggestionProvider<FabricClientCommandSource> suggests;

    public RegistryListFeature(String name, boolean enabled, List<T> extraData, Registry<T> registry) {
        super(name, enabled);
        this.list = new ArrayList<>(extraData);
        this.registry = registry;
        this.suggests = (commandContext, suggestionsBuilder) -> {
            List<ResourceLocation> values = registry.stream().map(registry::getKey).toList();
            return SharedSuggestionProvider.suggestResource(values, suggestionsBuilder);
        };
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public List<LiteralArgumentBuilder<FabricClientCommandSource>> getSubCommands() {
        List<LiteralArgumentBuilder<FabricClientCommandSource>> subCommands = super.getSubCommands();
        subCommands.add(ClientCommandManager.literal("add")
                .then(ClientCommandManager.argument("entity", ResourceLocationArgument.id())
                        .suggests(suggests)
                        .executes(context -> {
                            ResourceLocation resourceLocation = context.getArgument("entity", ResourceLocation.class);
                            if (registry.containsKey(resourceLocation)) {
                                T entityType = registry.get(resourceLocation);
                                if (getList().contains(entityType)) {
                                    context.getSource().sendFeedback(Component.literal("Entity is already in the list"));
                                } else {
                                    getList().add(entityType);
                                    context.getSource().sendFeedback(Component.literal("Added " + resourceLocation + " to the list"));
                                }
                            } else {
                                context.getSource().sendError(Component.literal("Entity not found"));
                            }
                            return 1;
                        })
                ));
        subCommands.add(ClientCommandManager.literal("remove")
                .then(ClientCommandManager.argument("entity", ResourceLocationArgument.id())
                        .suggests((commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource(list.stream().map(registry::getKey).toList(), suggestionsBuilder))
                        .executes(context -> {
                            ResourceLocation resourceLocation = context.getArgument("entity", ResourceLocation.class);
                            if (Registry.ENTITY_TYPE.containsKey(resourceLocation)) {
                                T entityType = registry.get(resourceLocation);
                                if (getList().contains(entityType)) {
                                    getList().remove(entityType);
                                    context.getSource().sendFeedback(Component.literal("Removed " + resourceLocation + " from the list"));
                                } else {
                                    context.getSource().sendFeedback(Component.literal("Entity is not in the list"));
                                }
                            } else {
                                context.getSource().sendError(Component.literal("Entity not found"));
                            }
                            return 1;
                        })
                ));

        return subCommands;
    }
}
