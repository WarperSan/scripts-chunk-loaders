package io.nihlen.scriptschunkloaders;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptsChunkLoadersMod implements ModInitializer {
	public static final String MODID = "scripts-chunk-loaders";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final ChunkLoaderManager CHUNK_LOADER_MANAGER = new ChunkLoaderManager();
	// Add Custom TicketType, Flags are set to mimic FORCED.
	public static final TicketType CUSTOM_TICKETTYPE_FORCED =  Registry.register(BuiltInRegistries.TICKET_TYPE, ScriptsChunkLoadersMod.MODID, new TicketType(TicketType.NO_TIMEOUT, TicketType.FLAG_PERSIST | TicketType.FLAG_LOADING | TicketType.FLAG_SIMULATION | TicketType.FLAG_KEEP_DIMENSION_ACTIVE));

    public static final GameRule<Boolean> ALWAYS_SHOW_LOADER_NAME = Registry.register(BuiltInRegistries.GAME_RULE, "always_show_loader_name", GameRuleBuilder.forBoolean(true).category(GameRuleCategory.MISC).build());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ServerLifecycleEvents.SERVER_STARTED.register(CHUNK_LOADER_MANAGER::initialize);
	}

    public static boolean isCustomNameVisible(Level level) {
        MinecraftServer server = level.getServer();

        if (server == null)
            return true;

        return server.getGameRules().get(ALWAYS_SHOW_LOADER_NAME);
    }
}
