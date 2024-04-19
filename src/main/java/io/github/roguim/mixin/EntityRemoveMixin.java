package io.github.roguim.mixin;

import io.github.roguim.interfaces.EntityRemoveCallback;
import net.minecraft.client.world.ClientEntityManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.entity.EntityChangeListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets="net.minecraft.world.entity.EntityChangeListener$Listener")
public class EntityRemoveMixin {
    @Inject(method = "remove(LRemovalReason)V", at=@At("HEAD"), cancellable = true)
    private void onRemove(Entity.RemovalReason reason, CallbackInfo info) {
        ActionResult result = EntityRemoveCallback.EVENT.invoker().removal((Entity) (Object) this, reason);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
