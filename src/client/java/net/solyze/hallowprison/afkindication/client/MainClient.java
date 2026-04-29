package net.solyze.hallowprison.afkindication.client;

import net.fabricmc.api.ClientModInitializer;
import net.solyze.hallowprison.afkindication.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainClient implements ClientModInitializer {

    public static final List<UUID> IN_ZONE = new ArrayList<>();

    public MainClient() {
        Main.LOGGER.info("{} {} (Client) initializing...", Main.MOD_DISPLAY, Main.MOD_VERSION);
    }

    @Override
    public void onInitializeClient() {
        Main.LOGGER.info("{} {} (Client) has been initialized.", Main.MOD_DISPLAY, Main.MOD_VERSION);
    }
}
