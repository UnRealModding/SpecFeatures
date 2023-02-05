package com.unrealdinnerbone.specfeatures;

import com.mojang.logging.LogUtils;
import com.unrealdinnerbone.specfeatures.chat.api.IChatFilter;
import net.minecraft.network.chat.Component;
import com.unrealdinnerbone.specfeatures.chat.AchievementFilter;
import com.unrealdinnerbone.specfeatures.chat.ChatType;
import com.unrealdinnerbone.specfeatures.chat.PlayerDamagePlayerFilter;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.LazyHashMap;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ChatFilter {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final LazyHashMap<ChatType, Pattern> patterns = new LazyHashMap<>(chatType -> Pattern.compile(chatType.regex()));
    private static final Map<ChatType, IChatFilter> filters = new HashMap<>();

    static {
        filters.put(ChatType.ACHIEVEMENT, new AchievementFilter());
        filters.put(ChatType.PLAYER_DAMAGE_PLAYER, new PlayerDamagePlayerFilter());
    }

    public static Component filter(Component component) {
        try {
            String fixedName = component.getString().replace("[§BSI§F]", "[SI]").replaceFirst("\\[SI\\]", "").replace("§", "").replaceFirst(" ", "");
            for (ChatType chatType : ChatType.CHAT_TYPES) {
                if(patterns.get(chatType).matcher(fixedName).matches()) {
                    if(Features.CHAT_NARRATOR.isAllowed() && Features.CHAT_NARRATOR.getList().contains(chatType)) {
                        SpecFeatures.SPEC_NARRATOR.say(fixedName, false);
                    }
                    return filters.get(chatType).filter(component);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while filtering chat", e);
            return component;
        }

        return component;
    }

}
