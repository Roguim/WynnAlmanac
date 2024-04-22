package io.github.roguim.mixin;

import io.github.roguim.interfaces.LivingEntityDeathCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityDeathMixin {

    @Inject(method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V", at = @At("HEAD"), cancellable = true)
    private void onDeath(DamageSource damageSource, CallbackInfo info) {
        ActionResult result = LivingEntityDeathCallback.EVENT.invoker().interact((LivingEntity)(Object)this, damageSource);

        if(result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
