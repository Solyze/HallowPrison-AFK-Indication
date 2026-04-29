package net.solyze.hallowprison.afkindication;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.solyze.hallowprison.afkindication.config.ConfigManager;
import net.solyze.hallowprison.afkindication.config.MainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Main implements ModInitializer {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static final String MOD_ID = "hallowprison-afk-indication",
            MOD_DISPLAY = "HallowPrison AFK Indication",
            MOD_VERSION = "v1.0.2-for-MC-1.21.11";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_DISPLAY);

    private static final Vec3d CORNER_1 = new Vec3d(28, 111, -13);
    private static final Vec3d CORNER_2 = new Vec3d(40, 124, -23);
    public static final Box BOX = new Box(Main.CORNER_1, Main.CORNER_2);

    private ConfigManager configManager;

    public Main() {
        instance = this;
        LOGGER.info("{} {} initializing...", MOD_DISPLAY, MOD_VERSION);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("{} {} has been initialized.", MOD_DISPLAY, MOD_VERSION);

        this.configManager = new ConfigManager();
        this.configManager.loadConfigs();
        this.configManager.saveConfigs();
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
