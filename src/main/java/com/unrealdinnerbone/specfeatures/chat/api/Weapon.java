package com.unrealdinnerbone.specfeatures.chat.api;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.*;
import java.util.List;

public record Weapon(String icon, String name, String matches, int color, ChatFormatting... chatFormattings) {

    private static final List<Weapon> weapons = new ArrayList<>();
    private static final Weapon BOW = register("🏹", "Bow", "Bow Arrow", 0x000000);
    private static final Weapon BOW_SPECTRAL = register("🏹", "Bow Spectral Arrow", "Bow Spectral Arrow", Color.YELLOW.getRGB());
    private static final Weapon CROSSBOW = register("🏹", "Bow", "Crossbow Arrow", 0x000000, ChatFormatting.BOLD);
    private static final Weapon CROSSBOW_SPECTRAL = register("🏹", "Crossbow Spectral Arrow", "Bow Spectral Arrow", Color.YELLOW.getRGB(), ChatFormatting.BOLD);

    private static final Weapon TRIDENT = register("🔱", "Trident", "Trident", 0x000000);
    private static final Weapon TRIDENT_THROWN = register("🔱", "Thrown Trident", "Thrown Trident", 0x000000, ChatFormatting.BOLD);

    static {
        Map<String, Integer> materialMap = new HashMap<>();
        materialMap.put("Wood", 0x966F33);
        materialMap.put("Stone", 0x918E85);
        materialMap.put("Iron", 0xa19d94);
        materialMap.put("Gold", 0xFFD700);
        materialMap.put("Diamond", 0xb9f2ff);
        materialMap.put("Netherite", 0x403434);

        List<Pair<String, String>> weapons = new ArrayList<>();
        weapons.add(Pair.of("Sword", "🗡"));
        weapons.add(Pair.of("Axe", "🪓"));


        for (Pair<String, String> weapon : weapons) {
            String weaponName = weapon.getFirst();
            String weaponIcon = weapon.getSecond();
            for (Map.Entry<String, Integer> material : materialMap.entrySet()) {
                String materialName = material.getKey();
                int color = material.getValue();
                register(weaponIcon, materialName + " " + weaponName, materialName + " " + weaponName, color);
            }
        }
    }


    private static Weapon register(String icon, String name, String matches, int color, ChatFormatting... chatFormattings) {
        Weapon weapon = new Weapon(icon, name, matches, color, chatFormattings);
        weapons.add(weapon);
        return weapon;
    }
    public static Optional<Weapon> getWeapon(String name) {
        return weapons.stream().filter(weapon -> name.contains(weapon.matches())).findFirst();
    }
    public static Component reformat(Component component) {
        return getWeapon(component.getString())
                .map(weapon -> Component.literal(weapon.icon()).withStyle(component.getStyle().withColor(weapon.color())).withStyle(weapon.chatFormattings()))
                .orElseGet(component::copy);

    }

    public static List<Weapon> getWeapons() {
        return weapons;
    }
}
