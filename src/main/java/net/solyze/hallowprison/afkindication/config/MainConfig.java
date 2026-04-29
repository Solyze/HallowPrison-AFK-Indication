package net.solyze.hallowprison.afkindication.config;

import net.solyze.hallowprison.afkindication.Main;

@ConfigInfo(name = Main.MOD_ID + "/config")
public class MainConfig {

    private boolean prefixEnabled = true;

    public boolean isPrefixEnabled() {
        return prefixEnabled;
    }

    public void setPrefixEnabled(boolean prefixEnabled) {
        this.prefixEnabled = prefixEnabled;
    }
}
