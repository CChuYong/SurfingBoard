package kr.chuyong.surfingboard;

import kr.chuyong.surfingboard.api.SurfServerAPI;
import kr.chuyong.surfingboard.api.SurfingAPI;
import kr.chuyong.surfingboard.api.impl.SurfServerAPIImpl;
import kr.chuyong.surfingboard.api.impl.MockedPacketSurfingAPI;
import kr.chuyong.surfingboard.command.SurfCommand;
import kr.chuyong.surfingboard.datasource.MySQLSurfServerDataSource;
import kr.chuyong.surfingboard.datasource.SurfServerDataSource;
import kr.chuyong.surfingboard.datasource.YamlSurfServerDataSource;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SurfingBoard extends JavaPlugin {
    private static SurfServerAPI surfServerAPI;
    private static SurfingAPI surfingAPI;

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        this.saveDefaultConfig();

        String dataSourceType = getConfig().getString("datasource", "YAML");
        getLogger().info("Loading plugin using DataSource: " + dataSourceType + "...");
        SurfServerDataSource dataSource = switch(dataSourceType.toLowerCase()) {
            case "yaml" -> new YamlSurfServerDataSource(getConfig());
            case "mysql" -> new MySQLSurfServerDataSource(getConfig().getConfigurationSection("mysql"));
            default -> throw new IllegalArgumentException("DataSource type must be yaml or mysql");
        };
        dataSource.initialize();
        getLogger().info("Datasource initialization completed!");

        ConfigurationSection section = getConfig().getConfigurationSection("surfing");
        if(section == null) throw new IllegalArgumentException("Surfing section must be defined in config.yml");

        SurfingConfiguration surfingConfiguration = new SurfingConfiguration(
                section.getLong("serverUpdatePeriod", 1200L)
        );


        getLogger().info("Initializing default SurfServer systems...");
        SurfServerAPIImpl surfServerAPIImpl = new SurfServerAPIImpl(this, dataSource, surfingConfiguration);
        surfServerAPIImpl.initialize();

        MockedPacketSurfingAPI surfingAPIImpl = new MockedPacketSurfingAPI(surfServerAPIImpl);

        surfServerAPI = surfServerAPIImpl;
        surfingAPI = surfingAPIImpl;

        SurfCommand surfCommand = new SurfCommand(surfServerAPI, surfingAPIImpl);
        Objects.requireNonNull(getCommand("surf")).setExecutor(surfCommand);
        Objects.requireNonNull(getCommand("surf")).setTabCompleter(surfCommand);

        long elapsed = System.currentTimeMillis() - startTime;
        getLogger().info("Plugin loaded successfully! (took " + elapsed + "ms)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SurfServerAPI getServerAPI() {
        return surfServerAPI;
    }

    public static SurfingAPI getSurfingAPI() {
        return surfingAPI;
    }
}
