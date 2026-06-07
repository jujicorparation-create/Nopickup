package com.nopickup;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

public class NoPickupMod implements ClientModInitializer {

    public static final String[] BLOCKED_ITEMS = {
        "helmet", "chestplate", "leggings", "boots",
        "sword", "pickaxe", "axe", "shovel",
        "potion",
        "enchanted_book",
        "experience_bottle",
        "golden_carrot",
        "coal",
        "cobblestone",
        "lapis_lazuli"
    };

    @Override
    public void onInitializeClient() {
    }

    public static boolean shouldBlock(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        String itemId = Registries.ITEM.getId(stack.getItem()).toString();
        for (String blocked : BLOCKED_ITEMS) {
            if (itemId.contains(blocked)) return true;
        }
        return false;
    }
                                      }
