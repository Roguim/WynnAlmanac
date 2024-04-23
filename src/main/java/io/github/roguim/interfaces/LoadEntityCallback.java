package io.github.roguim.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.Optional;

public interface LoadEntityCallback {
    Event<LoadEntityCallback> EVENT = EventFactory.createArrayBacked(
            LoadEntityCallback.class,
            (listeners) -> (entity, world) -> {
                for (LoadEntityCallback listener : listeners) {
                    ActionResult result = listener.interact(entity, world);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            }
    );

    ActionResult interact(Entity entity, World world);
}
