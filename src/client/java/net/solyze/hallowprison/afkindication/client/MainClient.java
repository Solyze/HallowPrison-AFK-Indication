package net.solyze.hallowprison.afkindication.client;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.solyze.hallowprison.afkindication.Main;
import net.solyze.hallowprison.afkindication.config.ConfigManager;
import net.solyze.hallowprison.afkindication.config.MainConfig;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class MainClient implements ClientModInitializer {

    public static final Set<UUID> IN_ZONE = new HashSet<>();

    public MainClient() {
        Main.LOGGER.info("{} {} (Client) initializing...", Main.MOD_DISPLAY, Main.MOD_VERSION);
    }

    @Override
    public void onInitializeClient() {
        Main.LOGGER.info("{} {} (Client) has been initialized.", Main.MOD_DISPLAY, Main.MOD_VERSION);

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            registerCommands(dispatcher);
        });
    }

    private void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                ClientCommandManager.literal("afkindicator")
                        .then(ClientCommandManager.literal("debug")
                                .executes(context -> {
                                    MinecraftClient client = MinecraftClient.getInstance();

                                    if (client.player == null) {
                                        context.getSource().sendFeedback(Text.literal("No player."));
                                        return 1;
                                    }

                                    UUID uuid = client.player.getUuid();
                                    boolean afk = MainClient.IN_ZONE.contains(uuid);

                                    context.getSource().sendFeedback(Text.literal("AFK: " + afk));
                                    return 1;
                                })
                        )
                        .then(ClientCommandManager.literal("toggleprefix")
                                .executes(context -> {
                                    ConfigManager configManager = Main.getInstance().getConfigManager();
                                    Optional<Object> optional = configManager.getConfig(MainConfig.class);

                                    if (optional.isPresent()) {
                                        MainConfig config = (MainConfig) optional.get();

                                        boolean newState = !config.isPrefixEnabled();
                                        config.setPrefixEnabled(newState);

                                        configManager.saveConfigs();

                                        context.getSource().sendFeedback(Text.literal("The AFK prefix is now " + (newState ? "enabled" : "disabled") + "."));
                                    }
                                    return 1;
                                })
                        )
        );
    }
}
