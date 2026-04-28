package net.solyze.hallowprison.afkindication.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.solyze.hallowprison.afkindication.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(method = "collectPlayerEntries", at = @At("RETURN"), cancellable = true)
    private void collectPlayerEntries(CallbackInfoReturnable<List<PlayerListEntry>> cir) {
        ClientWorld level = MinecraftClient.getInstance().world;
        if (level == null) return;
        List<PlayerListEntry> infos = new ArrayList<>();

        Set<UUID> inZone = level.getEntitiesByClass(PlayerEntity.class, Main.BOX, (p) -> true)
                .stream()
                .map(Entity::getUuid)
                .collect(Collectors.toSet());

        for (PlayerListEntry playerInfo : cir.getReturnValue()) {
            if (inZone.contains(playerInfo.getProfile().id())) {
                playerInfo.setListOrder(-999);
                Text component = playerInfo.getDisplayName();

                if (component != null) {
                    playerInfo.setDisplayName(this.cleanComponent(component));
                }
            }

            infos.add(playerInfo);
        }

        cir.setReturnValue(infos);
    }

    @Unique
    private MutableText cleanComponent(Text component) {
        boolean bold = component.getStyle().isBold();

        MutableText result = component.copy();

        result.setStyle(
                component.getStyle()
                        .withBold(bold)
                        .withItalic(false)
                        .withUnderline(false)
                        .withStrikethrough(false)
                        .withObfuscated(false)
                        .withColor(Formatting.DARK_GRAY)
        );

        result.getSiblings().clear();

        for (Text sibling : component.getSiblings()) {
            result.append(cleanComponent(sibling));
        }

        return result;
    }
}