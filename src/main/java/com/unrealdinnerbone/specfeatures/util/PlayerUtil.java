package com.unrealdinnerbone.specfeatures.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;

public class PlayerUtil {

    public static Player getSelf() {
        return Minecraft.getInstance().player;
    }

    public static GameType getGameMode() {
        return getGameMode(getSelf());
    }

    public static GameType getGameMode(Player player) {
        return Minecraft.getInstance().getConnection().getPlayerInfo(player.getUUID()).getGameMode();
    }
}
