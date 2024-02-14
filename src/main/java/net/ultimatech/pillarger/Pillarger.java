package net.ultimatech.pillarger;

import net.fabricmc.api.ModInitializer;

import net.ultimatech.pillarger.block.PLBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pillarger implements ModInitializer {

	public static final String MOD_ID = "pillarger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		PLBlocks.registerModBlocks();

		LOGGER.info("Pillarger mod initialized.");
	}
}