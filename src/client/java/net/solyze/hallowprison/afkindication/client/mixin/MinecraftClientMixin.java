package net.solyze.hallowprison.afkindication.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.solyze.hallowprison.afkindication.Main;
import net.solyze.hallowprison.afkindication.client.MainClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) return;

        MainClient.IN_ZONE.clear();

        for (PlayerEntity player : world.getPlayers()) {
            if (Main.BOX.contains(new Vec3d(player.getX(), player.getY(), player.getZ()))) {
                MainClient.IN_ZONE.add(player.getUuid());
            }
        }
    }
}