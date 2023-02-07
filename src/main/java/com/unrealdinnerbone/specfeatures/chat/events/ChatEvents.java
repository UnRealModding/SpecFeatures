package com.unrealdinnerbone.specfeatures.chat.events;

import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.MessageSystem;
import net.kyori.adventure.text.Component;

import java.util.List;

public class ChatEvents {

    public static void onAchievement(Component achievement, String message) {
        if(Features.W_ACHIEVEMENT.isAllowed()) {
            String[] split = message.replace("[SI]", "").split(" ");
            if (split.length > 1) {
                String name = split[1];
                switch (Features.W_ACHIEVEMENT.getEnumValue()) {
                    case CHAT -> MessageSystem.addMessage(createOrderList(name));
                    case MSG -> MessageSystem.addCommand(createOrderListCommand(name));
                }
            }
        }
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
