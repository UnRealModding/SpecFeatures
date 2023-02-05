package com.unrealdinnerbone.specfeatures.features;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.unrealdinnerbone.specfeatures.api.Feature;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ListFeature<T> extends Feature {

    private final List<T> list;
    private final Function<String, T> stringToValue;

    private final Function<T, String> stringMap;

    private final SuggestionProvider<FabricClientCommandSource> suggests;

    public ListFeature(String name, boolean enabled, List<T> extraData, List<String> values, Function<T, String> stringMap, Function<String, T> stringToValue) {
        super(name, enabled);
        this.list = new ArrayList<>(extraData);
        this.stringToValue = stringToValue;
        this.stringMap = stringMap;
        this.suggests = (commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggest(values, suggestionsBuilder);
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public List<LiteralArgumentBuilder<FabricClientCommandSource>> getSubCommands() {
        List<LiteralArgumentBuilder<FabricClientCommandSource>> subCommands = super.getSubCommands();
        subCommands.add(ClientCommandManager.literal("add")
                .then(ClientCommandManager.argument("value", StringArgumentType.word())
                        .suggests(suggests)
                        .executes(context -> {
                            String value = StringArgumentType.getString(context, "value");
                            T t = stringToValue.apply(value);
                            if (getList().contains(t)) {
                                context.getSource().sendFeedback(Component.literal("Value is already in the list"));
                            } else {
                                if(t == null) {
                                    context.getSource().sendError(Component.literal("Value not found"));
                                    return 0;
                                }else {
                                    getList().add(t);
                                    context.getSource().sendFeedback(Component.literal("Added " + value + " to the list"));
                                }
                            }
                            return 1;
                        })
                ));
        subCommands.add(ClientCommandManager.literal("remove")
                .then(ClientCommandManager.argument("value", StringArgumentType.word())
                        .suggests((commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggest(list.stream().map(stringMap).toList(), suggestionsBuilder))
                        .executes(context -> {
                            String value = StringArgumentType.getString(context, "value");
                            T t = stringToValue.apply(value);
                            if (getList().contains(t)) {
                                getList().remove(t);
                                context.getSource().sendFeedback(Component.literal("Removed " + value + " from the list"));
                            } else {
                                context.getSource().sendFeedback(Component.literal("Value is not in the list"));
                            }
                            return 1;
                        })
                ));

        return subCommands;
    }
}
