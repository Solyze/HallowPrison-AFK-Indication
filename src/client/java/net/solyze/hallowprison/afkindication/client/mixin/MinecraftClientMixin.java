package net.solyze.hallowprison.afkindication.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.solyze.hallowprison.afkindication.Main;
import net.solyze.hallowprison.afkindication.client.MainClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) return;

        for (PlayerEntity player : world.getPlayers()) {
            UUID uuid = player.getUuid();

            if (Main.BOX.contains(player.getBlockPos())) {
                MainClient.IN_ZONE.add(uuid);
            } else {
                MainClient.IN_ZONE.remove(uuid);
            }
        }
    }
}