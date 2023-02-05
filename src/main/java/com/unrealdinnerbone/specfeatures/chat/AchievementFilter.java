package com.unrealdinnerbone.specfeatures.chat;

import com.unrealdinnerbone.specfeatures.chat.api.IChatFilter;
import net.minecraft.network.chat.Component;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.MessageSystem;

import java.util.List;

public class AchievementFilter implements IChatFilter {
    @Override
    public Component filter(Component component) {
        String message = component.getString().replace("[SI]", "");
        String[] split = message.split(" ");
        if(split.length > 1) {
            String name = split[1];
            if(Features.W_ACHIEVEMENT.isAllowed()) {
                switch (Features.W_ACHIEVEMENT.getEnumValue()) {
                    case CHAT -> MessageSystem.addMessage(createOrderList(name));
                    case MSG -> MessageSystem.addCommand(createOrderListCommand(name));
                }
            }
        }
        return component;
    }
    public static List<String> createOrderList(String name) {
        return List.of("W achievement " + name,
                "W achievements " + name,
                "Triple achievement W " + name,
                "Quadruple achievement W " + name,
                "Massive achievement W " + name);
    }

    public static List<String> createOrderListCommand(String name) {
        return List.of("msg " + name + " W achievement",
                "msg " + name + " W achievements",
                "msg " + name + " Triple achievement W",
                "msg " + name + " Quadruple achievement W",
                "msg " + name + " Massive achievement W");
    }
}
