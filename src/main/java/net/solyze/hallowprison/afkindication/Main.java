package net.solyze.hallowprison.afkindication;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Main implements ModInitializer {

    private static final Vec3d CORNER_1 = new Vec3d(28, 111, -13);
    private static final Vec3d CORNER_2 = new Vec3d(40, 124, -23);
    public static final Box BOX = new Box(Main.CORNER_1, Main.CORNER_2);

    @Override
    public void onInitialize() {

    }
}
