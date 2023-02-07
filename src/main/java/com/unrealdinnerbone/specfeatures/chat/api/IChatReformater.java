package com.unrealdinnerbone.specfeatures.chat.api;

import net.minecraft.network.chat.Component;

public interface IChatReformater {
    Component filter(Component component);
}
