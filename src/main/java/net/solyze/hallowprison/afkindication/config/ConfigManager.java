package net.solyze.hallowprison.afkindication.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.solyze.hallowprison.afkindication.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class ConfigManager {

    private final Gson gson;
    private final HashMap<String, Object> configs = new HashMap<>();

    public ConfigManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void loadConfigs() {
        Main.LOGGER.info("Loading configs...");
        this.loadConfig(MainConfig.class);
    }

    public void saveConfigs() {
        Main.LOGGER.info("Saving configs...");
        this.saveConfig(MainConfig.class);
    }

    public void loadConfig(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(ConfigInfo.class)) {
            Main.LOGGER.error("\"{}\" has not been annotated with {}!", clazz.getName(), ConfigInfo.class.getSimpleName());
            return;
        }
        ConfigInfo config = clazz.getAnnotation(ConfigInfo.class);
        File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), config.name() + ".json");
        Main.LOGGER.info(file.getAbsolutePath());
        if (!file.getParentFile().exists()) //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
        try {
            if (!file.exists() && file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("{}");
                    Main.LOGGER.info("Config created: \"{}.json\"", config.name());
                }
            }
        } catch (IOException ex) {
            Main.LOGGER.error("Could not create config \"{}\"!", file.getAbsolutePath(), ex);
        }
        try (FileReader reader = new FileReader(file)) {
            this.configs.put(config.name(), this.gson.fromJson(reader, clazz));
            Main.LOGGER.info("Config loaded: \"{}.json\"", config.name());
        } catch (IOException ex) {
            Main.LOGGER.error("Could not load config \"{}.json\"!", config.name(), ex);
        }
    }

    public void saveConfig(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(ConfigInfo.class)) {
            Main.LOGGER.error("\"{}\" has not been annotated with {}!", clazz.getName(), ConfigInfo.class.getSimpleName());
            return;
        }
        ConfigInfo config = clazz.getAnnotation(ConfigInfo.class);
        Object object = this.configs.get(config.name());
        if (object == null) return;
        String json = gson.toJson(object);
        String path = new File(FabricLoader.getInstance().getConfigDir().toFile(),
                config.name() + ".json").getAbsolutePath();
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
            Main.LOGGER.info("Config saved: \"{}.json\"", config.name());
        } catch (IOException ex) {
            Main.LOGGER.error("Could not save config \"{}.json\"!", config.name(), ex);
        }
    }

    public Optional<Object> getConfig(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(ConfigInfo.class)) {
            Main.LOGGER.error("\"{}\" has not been annotated with {}!", clazz.getName(), ConfigInfo.class.getSimpleName());
            return Optional.empty();
        }
        ConfigInfo config = clazz.getAnnotation(ConfigInfo.class);
        return Optional.ofNullable(configs.get(config.name()));
    }
}
