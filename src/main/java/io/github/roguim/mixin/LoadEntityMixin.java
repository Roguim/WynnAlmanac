package io.github.roguim.mixin;

import io.github.roguim.interfaces.LivingEntityDeathCallback;
import io.github.roguim.interfaces.LoadEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EntityType.class)
public class LoadEntityMixin {

    @Inject(at = @At(value = "RETURN"), method = "loadEntityFromNbt", cancellable = true)
    private static void onLoad(NbtCompound nbt, World world, CallbackInfoReturnable<Optional<Entity>> info) {
        Optional<Entity> returnValue = info.getReturnValue();
        ActionResult result;
        if(returnValue.isPresent()) {
            result = LoadEntityCallback.EVENT.invoker().interact((Entity) ((Object) returnValue.get()), world);
        } else {
            result = ActionResult.PASS;
        }


        if(result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
