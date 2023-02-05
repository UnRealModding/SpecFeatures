package com.unrealdinnerbone.specfeatures.chat;

import java.util.ArrayList;
import java.util.List;

public record ChatType(String name, String regex) {

    public static List<ChatType> CHAT_TYPES = new ArrayList<>();
    public static ChatType ACHIEVEMENT = register("achievement", "^[a-zA-Z0-9_]{2,16} has earned the achievement [A-Za-z0-9 ]+");
    public static ChatType PLAYER_DAMAGE_PLAYER = register("player_damage_player", "^[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16}+ -\\s[a-zA-Z ]+\\) \\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%\\]\\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%\\]$");
    public static ChatType MYSTERY_DAMAGE_PLAYER = register("mystery_damage_player", "\\\"^[a-zA-Z0-9_]{2,16} » \\\\\\\\(\\\\\\\\?\\\\\\\\?\\\\\\\\?\\\\\\\\) \\\\\\\\[([-+]?(?=\\\\\\\\.\\\\\\\\d|\\\\\\\\d)(?:\\\\\\\\d+)?(?:\\\\\\\\.?\\\\\\\\d*))(?:[eE]([-+]?\\\\\\\\d+))?%\\\\\\\\]\\\\\\\\[([-+]?(?=\\\\\\\\.\\\\\\\\d|\\\\\\\\d)(?:\\\\\\\\d+)?(?:\\\\\\\\.?\\\\\\\\d*))(?:[eE]([-+]?\\\\\\\\d+))?%\\\\\\\\]$\\\"");

    public static ChatType PLAYER_DAMAGE = register("player_damage", "[a-zA-Z0-9_]{2,16}+ » \\([A-Za-z0-9 ']+\\) \\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%\\]\\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%\\]");

    public static ChatType OPEN_CHEST = register("open_chest","^[a-zA-Z0-9_]{2,16} »[a-zA-Z ]+ Chest");

    public static ChatType DISABLE_SHIELD = register("disable_sheild","^[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16} - Shield Disable\\)");

    public static ChatType CRAFT = register("craft","^[a-zA-Z0-9_]{2,16} » \\[Craft\\] [A-Za-z0-9 ]+");

    public static ChatType MINE_ORE = register("mine_ore","^[a-zA-Z0-9_]{2,16} » [A-Za-z0-9 ]+ \\[[0-9]+\\]\\[[0-9]+\\]");

    public static ChatType MINE_UNEXPOSED_ORE = register("mine_unexposed_ore","^[a-zA-Z0-9_]{2,16} » Mined a potentially unexposed ore.");

    public static ChatType ORE_REMOVED = register("ore_removed","Removed [a-zA-Z ]+ vein near [a-zA-Z0-9_]{2,16}");

    public static ChatType EAT_GOLDEN_APPLE = register("eat_golden_apple","[a-zA-Z0-9_]{2,16} » Golden Apple \\[[0-9]+\\]");

    public static ChatType EAT_GOLDEN_HEAD = register("eat_golden_head","[a-zA-Z0-9_]{2,16} » Golden Head \\[[0-9]+\\]");

    public static ChatType EAT_E_GOLDEN_APPLE = register("eat_e_golden_apple","[a-zA-Z0-9_]{2,16} » Enchanted Golden Apple \\[[0-9]+\\]");

    public static ChatType TOTEM_OF_UNDYING = register("totem_of_undying","[a-zA-Z0-9_]{2,16} » Totem of Undying \\[[0-9]+\\]");

    public static ChatType IPVP = register("ipvp","[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16} - iPvP\\)");

    public static ChatType PORTAL = register("portal","[a-zA-Z0-9_]{2,16} » Portal \\[[a-zA-Z]+\\]");

    public static ChatType GOOMBA_STOMP = register("goomba_stomp","[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16} - Goomba Stomp\\)");

    public static ChatType REACH = register("reach","[a-zA-Z0-9_]{2,16} » Reach \\[[0-9]*\\.[0-9]+\\]\\[[0-9]+\\]\\[[0-9]*\\.[0-9]+%\\]\\[[0-9]+\\]");

    public static ChatType REACH_OLD = register("reach_old","[a-zA-Z0-9_]{2,16} » Reach \\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?\\]\\[[0-9]+\\]");

    public static ChatType STOLEN_ITEM = register("stolen_item","[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16} - Stolen Item\\) \\[[a-zA-Z_]+]");

    public static ChatType TRADING_PLACES = register("trading_places","[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16} - Trading Places\\)");

    public static ChatType WARDEN_ANGER = register("warden_anger","[a-zA-Z0-9_]{2,16} » Warden Anger");

    public static ChatType LAFX_TEAM = register("lafx_team","[a-zA-Z0-9_]{2,16}, [a-zA-Z0-9_]{2,16} \\[[0-9]+ blocks\\]$");

    public static ChatType TRADER_LLAMA_SPIT = register("trader_llama_spit","[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16}'s Trader Llama Spit\\) \\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%\\]\\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%\\]");

    public static ChatType EAT_STEW = register("eat_stew","[a-zA-Z0-9_]{2,16} » [a-zA-Z ]+ Suspicious Stew");

    public static ChatType CRAFT_DIAMOND_LEGGINGS = register("craft_diamond_leggings","[a-zA-Z0-9_]{2,16} » \\(C_money Diamond Leggings Craft\\) \\[[0-9]+\\]");

    public static ChatType PERSONS_WOLF = register("persons_wolf","[a-zA-Z0-9_]{2,16} » \\([a-zA-Z0-9_]{2,16}'s Wolf\\) \\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%]\\[([-+]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([-+]?\\d+))?%]");

    public static ChatType GOLDEN_APPLE_2 = register("golden_apple_2","[a-zA-Z0-9_]{2,16} » Golden Apple");

    public static ChatType TEST = register("test","Test");
    public static ChatType register(String name, String regex) {
        ChatType chatType = new ChatType(name, regex);
        CHAT_TYPES.add(chatType);
        return chatType;
    }
}
