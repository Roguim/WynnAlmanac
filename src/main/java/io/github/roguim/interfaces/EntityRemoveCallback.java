package io.github.roguim.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

/**
 * Callback for when an entity is removed, per ClientEntityManager.class
 *  Client-side event for when an entity is no longer being rendered for some reason
 *  Upon return:
 *   - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 *   - All other ActionResults lead to cancellation.
 */
public interface EntityRemoveCallback {
    Event<EntityRemoveCallback> EVENT = EventFactory.createArrayBacked(EntityRemoveCallback.class,
            (listeners) -> (entity, removalReason) -> {
                for (EntityRemoveCallback listener : listeners) {
                    ActionResult result = listener.removal(entity, removalReason);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult removal(Entity entity, Entity.RemovalReason removalReason);
}
