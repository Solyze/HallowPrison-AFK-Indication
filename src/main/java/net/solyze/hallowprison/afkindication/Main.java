package net.solyze.hallowprison.afkindication;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

    public static final String MOD_ID = "hallowprison-afk-indication",
            MOD_DISPLAY = "HallowPrison AFK Indication",
            MOD_VERSION = "v1.0.1-for-MC-1.21.11";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_DISPLAY);

    private static final Vec3d CORNER_1 = new Vec3d(28, 111, -13);
    private static final Vec3d CORNER_2 = new Vec3d(40, 124, -23);
    public static final Box BOX = new Box(Main.CORNER_1, Main.CORNER_2);

    public Main() {
        LOGGER.info("{} {} initializing...", MOD_DISPLAY, MOD_VERSION);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("{} {} has been initialized.", MOD_DISPLAY, MOD_VERSION);
    }
}
