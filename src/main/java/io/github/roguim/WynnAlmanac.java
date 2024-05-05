package io.github.roguim;

import io.github.roguim.interfaces.LivingEntityDeathCallback;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WynnAlmanac implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("wynnalmanac");

	@Override
	public void onInitialize() {
		LOGGER.info("[WynnAlmanac] Booting up...");

		LivingEntityDeathCallback.EVENT.register(((entity, reason) -> {
			Text name = entity.getCustomName();

			LOGGER.info("[WynnAlmanac] Event triggered.");

			if(name != null) {
				LOGGER.info("[WynnAlmanac] Name exists!");
				String named = name.getString();
				// TODO Also put this in a config
				Pattern matchPattern = Pattern.compile("§[ace][A-Za-z0-9? §]+\\[Lv. (([0-9])|(\\?))+]");
				Matcher matcher = matchPattern.matcher(named);
				// TODO Put the area / delay into a config file so this can be changed without rebooting, this is too damn painful.
				List<Entity> entityList = entity.getWorld().getOtherEntities(entity, Box.of(entity.getPos(),2,2,2));
				if (matcher.find()) {
					LOGGER.info("[WynnAlmanac] Match found: " + named);
					LOGGER.info("[WynnAlmanac] Other entity count: " + entityList.size());
				} else {
					// THIS CASE HAPPENS MORE OFTEN THAN NOT, DUE TO WYNN RENAMING THE BASE MOB TO THE DAMAGE TAKEN
					// Either needs a tick or two of delay before running the code, or a larger area.
					// Once delayed, then check again.
					LOGGER.warn("[WynnAlmanac] " + named);
					LOGGER.info("[WynnAlmanac] Other entity count: " + entityList.size());
				}
			}

			return ActionResult.PASS;
		}));
	}



}