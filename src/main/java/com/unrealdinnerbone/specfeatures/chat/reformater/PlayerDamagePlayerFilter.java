package com.unrealdinnerbone.specfeatures.chat.reformater;

import com.unrealdinnerbone.specfeatures.chat.api.IChatReformater;
import com.unrealdinnerbone.specfeatures.chat.api.Weapon;
import com.unrealdinnerbone.specfeatures.features.Features;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class PlayerDamagePlayerFilter implements IChatReformater {
    @Override
    public Component filter(Component component) {
        if (Features.FANCY_CHAT.isAllowed()) {
            Component attacked = component.getSiblings().get(0);
            Component attacker = component.getSiblings().get(3);
            Component weapon = component.getSiblings().get(4);
            Component damage = component.getSiblings().get(7);
            double damageValue = Double.parseDouble(damage.getString().replace("%", ""));
            Component healthLeft = component.getSiblings().get(9);
            double health = Double.parseDouble(healthLeft.getString().replace("%", ""));
            Component weaponCompent = Weapon.reformat(weapon);
            return Component.literal("[")
                    .append(Component.literal("SI").withStyle(ChatFormatting.AQUA))
                    .append(Component.literal("] "))
                    .append(attacker)
                    .append(" ")
                    .append(weaponCompent)
                    .append(Component.literal("x" + damageValue).withStyle(ChatFormatting.RED))
                    .append(" ")
                    .append(attacked)
                    .append("[")
                    .append(Component.literal(health + "%").withStyle(ChatFormatting.RED))
                    .append(Component.literal("]"))
                    .withStyle(component.getStyle());
        }
        return component;
    }

}
