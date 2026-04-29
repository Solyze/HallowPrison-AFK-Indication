package net.solyze.hallowprison.afkindication.client.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.solyze.hallowprison.afkindication.Main;
import net.solyze.hallowprison.afkindication.client.MainClient;
import net.solyze.hallowprison.afkindication.config.ConfigManager;
import net.solyze.hallowprison.afkindication.config.MainConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
    private void onGetPlayerName(PlayerListEntry entry, CallbackInfoReturnable<Text> info) {
        if (!MainClient.IN_ZONE.contains(entry.getProfile().id())) return;

        Text original = info.getReturnValue();

        MutableText grayName = Text.empty();
        original.visit((style, string) -> {
            grayName.append(Text.literal(string).setStyle(style.withColor(Formatting.DARK_GRAY)));
            return Optional.empty();
        }, Style.EMPTY);

        MutableText finalText = grayName;
        Main main = Main.getInstance();

        if (main != null) {
            ConfigManager configManager = main.getConfigManager();

            if (configManager != null) {
                Optional<Object> optional = configManager.getConfig(MainConfig.class);

                if  (optional.isPresent()) {
                    MainConfig config = (MainConfig) optional.get();

                    if (config.isPrefixEnabled()) {
                        MutableText prefix = Text.literal("[AFK] ").formatted(Formatting.DARK_GRAY);
                        finalText = prefix.append(grayName);
                    }
                }
            }
        }


        info.setReturnValue(finalText);
    }
}