package com.unrealdinnerbone.specfeatures.chat;

import com.mojang.logging.LogUtils;
import com.unrealdinnerbone.specfeatures.SpecFeatures;
import com.unrealdinnerbone.specfeatures.chat.api.IChatReformater;
import com.unrealdinnerbone.specfeatures.chat.events.ChatEvents;
import net.kyori.adventure.platform.fabric.impl.service.GsonComponentSerializerProviderImpl;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.network.chat.Component;
import com.unrealdinnerbone.specfeatures.chat.reformater.PlayerDamagePlayerFilter;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.LazyHashMap;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class ChatEvent {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final LazyHashMap<ChatType, Pattern> patterns = new LazyHashMap<>(chatType -> Pattern.compile(chatType.regex()));
    private static final Map<ChatType, IChatReformater> filters = new HashMap<>();
    private static final Map<ChatType, List<BiConsumer<net.kyori.adventure.text.Component, String>>> chatListeners = new HashMap<>();

    static {

        chatListeners.put(ChatType.ACHIEVEMENT, List.of(ChatEvents::onAchievement));
        filters.put(ChatType.PLAYER_DAMAGE_PLAYER, new PlayerDamagePlayerFilter());
    }

    public static net.kyori.adventure.text.Component onChat(Component component) {
        net.kyori.adventure.text.Component newComponent = component.asComponent();
        try {
            String fixedName = PlainTextComponentSerializer.plainText().serialize(newComponent).replaceFirst("BSIF", "SI").replace("§BSI§F", "SI");
            LOGGER.info(fixedName);
            if(Features.JSON_CHAT.isAllowed()) {
                String json = GsonComponentSerializer.gson().serialize(newComponent);
                LOGGER.info(json);
            }
            for (ChatType chatType : ChatType.CHAT_TYPES) {
                if(patterns.get(chatType).matcher(fixedName).matches()) {
                    if(Features.CHAT_NARRATOR.isAllowed() && Features.CHAT_NARRATOR.getList().contains(chatType)) {
                        SpecFeatures.SPEC_NARRATOR.say(fixedName.replaceFirst("\\[SI\\] ", ""), false);
                    }
                    if(chatListeners.containsKey(chatType)) {
                        chatListeners.get(chatType).forEach(chatListener -> chatListener.accept(newComponent, fixedName));
                    }
                    return (filters.containsKey(chatType) ? filters.get(chatType).filter(component) : component).asComponent();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while filtering chat", e);
            return component.asComponent();
        }

        return component.asComponent();
    }

}
