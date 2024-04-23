package io.github.roguim;

import io.github.roguim.interfaces.LivingEntityDeathCallback;
import io.github.roguim.interfaces.LoadEntityCallback;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WynnAlmanac implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("wynnalmanac");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");

		/*ClientEntityEvents.ENTITY_UNLOAD.register((entity1, world) -> {
			Text ename = entity1.getCustomName();
			if(ename != null) {
				String enamed = ename.getString();
				LOGGER.info(enamed);
			}
		});*/

		LoadEntityCallback.EVENT.register((entity,world) -> {
			LOGGER.info("[WynnAlmanac] Event Triggered");
			Text name = entity.getCustomName();
			if(name != null) {
				String named = name.getString();
				LOGGER.info(named);
			}

            return ActionResult.PASS;
        });

		LivingEntityDeathCallback.EVENT.register(((entity, reason) -> {
			Text name = entity.getCustomName();



			if(name != null) {
				String named = name.getString();
				Pattern matchPattern = Pattern.compile("§c[A-Za-z0-9? §]+\\[Lv. (([0-9])|(\\?))+]");
				Matcher matcher = matchPattern.matcher(named);
				if (matcher.find()) {
					LOGGER.info(named);
					LOGGER.info(reason.getName());
					Entity attacker = reason.getAttacker();
					if (attacker != null) {
						LOGGER.info(attacker.getEntityName());
					} else {
						LOGGER.warn("No attacker found for " + named);
					}
					Entity attacker2 = entity.getLastAttacker();

					if(attacker2 != null) {
						LOGGER.info(attacker2.getEntityName());
					}
				}
			}

			return ActionResult.PASS;
		}));
	}



}