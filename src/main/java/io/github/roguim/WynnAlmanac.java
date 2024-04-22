package io.github.roguim;

import io.github.roguim.interfaces.LivingEntityDeathCallback;
import net.fabricmc.api.ModInitializer;

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

		LivingEntityDeathCallback.EVENT.register(((entity, reason) -> {
			Text name = entity.getCustomName();
			if(name != null) {
				String named = name.getString();
				Pattern matchPattern = Pattern.compile("§c[A-Za-z0-9? §]+\\[Lv. (([0-9])|(\\?))+]");
				Matcher matcher = matchPattern.matcher(named);
				if (matcher.find()) {
					LOGGER.info(named);
					LOGGER.info(reason.getName());
				}
			}

			return ActionResult.PASS;
		}));
	}



}