package com.nopickup;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

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
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            
            client.world.getEntities().forEach(entity -> {
                if (entity instanceof ItemEntity itemEntity) {
                    ItemStack stack = itemEntity.getStack();
                    if (shouldBlock(stack)) {
                        // Item pickup range dan chiqarib yuboramiz
                        itemEntity.setPickupDelay(32767);
                    }
                }
            });
        });
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
