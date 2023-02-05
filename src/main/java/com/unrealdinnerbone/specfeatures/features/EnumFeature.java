package com.unrealdinnerbone.specfeatures.features;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.unrealdinnerbone.specfeatures.api.Feature;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;

public class EnumFeature<T extends Enum<T>> extends Feature
{

    private final SuggestionProvider<FabricClientCommandSource> suggest;

    private T enumValue;

    public EnumFeature(String name, boolean enabled, T extraData, T[] values) {
        super(name, enabled);
        this.enumValue = extraData;
        suggest = (commandContext, suggestionsBuilder) -> SharedSuggestionProvider.suggest(Arrays.stream(values).map(Enum::name).toList(), suggestionsBuilder);
    }

    @Override
    public List<LiteralArgumentBuilder<FabricClientCommandSource>> getSubCommands() {
        List<LiteralArgumentBuilder<FabricClientCommandSource>> commands = super.getSubCommands();
        //set enumValue
        commands.add(ClientCommandManager.literal("set")
                .then(ClientCommandManager.argument("value", StringArgumentType.word())
                        .suggests(suggest)
                        .executes(context -> {
                            String value = StringArgumentType.getString(context, "value");
                            enumValue = ((T) Enum.valueOf(enumValue.getClass(), value));
                            context.getSource().sendFeedback(Component.literal("Set " + getName() + " to " + value));
                            return 1;
                        })));
        return commands;
    }

    public T getEnumValue() {
        return enumValue;
    }
}
