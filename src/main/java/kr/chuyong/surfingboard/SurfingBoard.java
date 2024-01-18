package kr.chuyong.surfingboard;

import kr.chuyong.surfingboard.api.SurfServerAPI;
import kr.chuyong.surfingboard.api.SurfingAPI;
import kr.chuyong.surfingboard.api.impl.SurfServerAPIImpl;
import kr.chuyong.surfingboard.api.impl.TemporaryPacketSurfingAPI;
import kr.chuyong.surfingboard.datasource.MySQLSurfServerDataSource;
import kr.chuyong.surfingboard.datasource.SurfServerDataSource;
import kr.chuyong.surfingboard.datasource.YamlSurfServerDataSource;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurfingBoard extends JavaPlugin {
    private static SurfServerAPI surfServerAPI;
    private static SurfingAPI surfingAPI;

    @Override
    public void onEnable() {
        String dataSourceType = getConfig().getString("datasource", "YAML");
        SurfServerDataSource dataSource = switch(dataSourceType.toLowerCase()) {
            case "yaml" -> new YamlSurfServerDataSource(getConfig());
            case "mysql" -> new MySQLSurfServerDataSource(getConfig().getConfigurationSection("mysql"));
            default -> throw new IllegalArgumentException("DataSource type must be yaml or mysql");
        };
        dataSource.initialize();

        ConfigurationSection section = getConfig().getConfigurationSection("surfing");
        if(section == null) throw new IllegalArgumentException("Surfing section must be defined in config.yml");

        SurfingConfiguration surfingConfiguration = new SurfingConfiguration(
                section.getLong("serverUpdatePeriod", 1200L)
        );


        SurfServerAPIImpl surfServerAPIImpl = new SurfServerAPIImpl(this, dataSource, surfingConfiguration);
        surfServerAPIImpl.initialize();

        TemporaryPacketSurfingAPI surfingAPIImpl = new TemporaryPacketSurfingAPI(surfServerAPIImpl);

        surfServerAPI = surfServerAPIImpl;
        surfingAPI = surfingAPIImpl;
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
