package com.unrealdinnerbone.specfeatures.features;

import com.unrealdinnerbone.specfeatures.api.Feature;
import com.unrealdinnerbone.specfeatures.api.MessageType;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import com.unrealdinnerbone.specfeatures.chat.ChatType;

import java.util.ArrayList;
import java.util.List;

public class Features
{
    public static final List<Feature> FEATURES = new ArrayList<>();
    public static RegistryListFeature<EntityType<?>> HIGHTLIGHT = register(new RegistryListFeature<>("highlight", false, List.of(EntityType.PLAYER), Registry.ENTITY_TYPE));
    public static RegistryListFeature<EntityType<?>> RENDER_HEALTH = register(new RegistryListFeature<>("render_health", true, List.of(EntityType.PLAYER), Registry.ENTITY_TYPE));
    public static BasicFeature FANCY_CHAT = register(new BasicFeature("fancy_chat", true));
    public static EnumFeature<MessageType> W_ACHIEVEMENT = register(new EnumFeature<>("w_achievement", true, MessageType.CHAT, MessageType.values()));
    public static BasicFeature HORSE_STATS = register(new BasicFeature("horse_stats", true));

    public static ListFeature<ChatType> CHAT_NARRATOR = register(new ListFeature<>("chat_narrator", true, List.of(ChatType.ACHIEVEMENT, ChatType.CRAFT_DIAMOND_LEGGINGS), ChatType.CHAT_TYPES.stream().map(ChatType::name).toList(), ChatType::name, type -> ChatType.CHAT_TYPES.stream().filter(chatType -> chatType.name().equalsIgnoreCase(type)).findFirst().orElse(null)));


    public static <T extends Feature> T register(T feature) {
        FEATURES.add(feature);
        return feature;
    }
}
