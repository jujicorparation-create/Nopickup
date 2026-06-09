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

    // playerTouch - player narsaning ustiga kelganda (ham server, ham clientda) ishlaydi
    @Inject(method = "playerTouch(Lnet/minecraft/entity/player/PlayerEntity;)void", at = @At("HEAD"), cancellable = true)
    private void onPlayerTouch(PlayerEntity player, CallbackInfo ci) {
        // 'this' orqali yerdagi ItemEntity-ni olamiz
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack stack = itemEntity.getStack();

        // Agar mod aktiv bo'lsa va yerda yotgan narsa keraksiz bo'lsa
        if (NoPickupMod.shouldBlock(stack)) {
            ci.cancel(); // Player narsaga tegsa ham, o'yin uni umuman ko'rmaydi (yerdan olmaydi)
        }
    }
}
