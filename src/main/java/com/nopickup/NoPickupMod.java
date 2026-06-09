package com.nopickup;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class NoPickupMod implements ModInitializer {
    public static final String MOD_ID = "nopickup";
    
    // Mod holati (boshlanishida o'chiq bo'ladi)
    private static boolean isActive = false;

    // Bloklanadigan narsalar ro'yxati
    private static final String[] BLOCKED_ITEMS = {
            "helmet", "chestplate", "leggings", "boots",
            "sword", "pickaxe", "axe", "shovel",
            "potion", "enchanted_book", "experience_bottle",
            "golden_carrot", "coal", "cobblestone", "lapis_lazuli",
            "glass_bottle"
    };

    private static KeyBinding activateKey;
    private static KeyBinding deactivateKey;

    @Override
    public void onInitialize() {
        // AKTIVLASHTIRISH = U tugmasi
        activateKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nopickup.activate",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U, // U harfi
                "category.nopickup"
        ));

        // DEAKTIVLASHTIRISH = I tugmasi
        deactivateKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nopickup.deactivate",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I, // I harfi
                "category.nopickup"
        ));

        // Har tickda tugma bosilganini tekshirish
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Agar U bosilsa -> Aktivlashadi
            if (activateKey.wasPressed()) {
                isActive = true;
                client.player.sendMessage(Text.literal("§aNoPickup Filtri Yoqildi! (U)"), false);
            }

            // Agar I bosilsa -> Deaktivlashadi
            if (deactivateKey.wasPressed()) {
                isActive = false;
                client.player.sendMessage(Text.literal("§cNoPickup Filtri O'chirildi! (I)"), false);
            }
        });
    }

    // MixIn klassingiz yerdagi narsani olishni bloklash uchun shu metodni tekshiradi
    public static boolean shouldBlock(ItemStack stack) {
        // Agar deactiv holatda bo'lsa (isActive = false), hech narsani bloklama
        if (!isActive) return false;

        if (stack == null || stack.isEmpty()) return false;

        String itemId = Registries.ITEM.getId(stack.getItem()).toString();
        for (String blocked : BLOCKED_ITEMS) {
            if (itemId.contains(blocked)) {
                return true; // Ro'yxatdagi narsa bo'lsa va mod Active bo'lsa - bloklaydi
            }
        }
        return false;
    }
}
