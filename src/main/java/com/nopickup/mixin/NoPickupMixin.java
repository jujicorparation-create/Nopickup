package com.nopickup.mixin;

import com.nopickup.NoPickupMod;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class NoPickupMixin {

    @Inject(method = "canPickupItem", at = @At("HEAD"), cancellable = true)
    private void onCanPickup(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (NoPickupMod.shouldBlock(stack)) {
            cir.setReturnValue(false);
        }
    }
        }
