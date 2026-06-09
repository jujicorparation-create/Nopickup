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

    // onPlayerCollision metodi barcha versiyalarda aniq (PlayerEntity)void deskriptoriga ega
    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        // 'this' orqali yerdagi ItemEntity obyektini olamiz
        ItemEntity itemEntity = (ItemEntity) (Object) this;
        ItemStack stack = itemEntity.getStack();

        // Agar mod yoqilgan bo'lsa va yerda yotgan narsa musor bo'lsa, olishni bekor qilamiz
        if (NoPickupMod.shouldBlock(stack)) {
            ci.cancel();
        }
    }
}
