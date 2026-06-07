package com.nopickup.mixin;

import com.nopickup.NoPickupMod;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class NoPickupMixin {

    @Inject(method = "attemptPickup", at = @At("HEAD"), cancellable = true)
    private void onAttemptPickup(ItemEntity itemEntity, CallbackInfo ci) {
        ItemStack stack = itemEntity.getStack();
        if (NoPickupMod.shouldBlock(stack)) {
            ci.cancel();
        }
    }
}
