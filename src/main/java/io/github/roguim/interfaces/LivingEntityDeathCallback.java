package io.github.roguim.interfaces;


import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface LivingEntityDeathCallback {
    Event<LivingEntityDeathCallback> EVENT = EventFactory.createArrayBacked(
            LivingEntityDeathCallback.class,
            (listeners) -> (entity, reason) -> {
                for (LivingEntityDeathCallback listener : listeners) {
                    ActionResult result = listener.interact(entity, reason);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
    );

    ActionResult interact(LivingEntity entity, DamageSource reason);
}
