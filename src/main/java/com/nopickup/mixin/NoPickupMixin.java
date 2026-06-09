package com.nopickup.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import com.nopickup.NoPickupMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class NoPickupMixin {

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onPickup(PlayerEntity player, CallbackInfo ci) {
        // 'this' orqali ItemEntity obyektini olamiz
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack stack = itemEntity.getStack();

        // Agar siz yaratgan NoPickupMod-da bloklash funksiyasi aktiv bo'lsa va element ro'yxatda bo'lsa
        if (NoPickupMod.shouldBlock(stack)) {
            ci.cancel(); // Player narsaga tegsa ham uni inventariga ololmaydi
        }
    }
}
