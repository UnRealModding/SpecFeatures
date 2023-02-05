package com.unrealdinnerbone.specfeatures.chat.api;

import net.minecraft.network.chat.Component;

public interface IChatFilter {
    Component filter(Component component);
}
