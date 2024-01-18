package kr.chuyong.surfingboard.api.impl;

import kr.chuyong.surfingboard.SurfServer;
import kr.chuyong.surfingboard.SurfingConfiguration;
import kr.chuyong.surfingboard.api.SurfServerAPI;
import kr.chuyong.surfingboard.datasource.SurfServerDataSource;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class SurfServerAPIImpl implements SurfServerAPI {
    private final Plugin plugin;
    private final SurfServerDataSource dataSource;
    private final SurfingConfiguration configuration;

    private Map<String, SurfServer> serverCache = new ConcurrentHashMap<>();

    public void initialize() {
        this.updateServers();
        plugin.getServer().getScheduler()
                .runTaskTimerAsynchronously(plugin, this::updateServers,
                        configuration.serverUpdatePeriod(), configuration.serverUpdatePeriod());
    }

    private void updateServers() {
        plugin.getLogger().info("Updating SurfServer cache...");
        List<SurfServer> servers = dataSource.loadFromDatabase();
        ConcurrentHashMap<String, SurfServer> newServerCache = new ConcurrentHashMap<>();
        for(SurfServer server : servers) {
            newServerCache.put(server.serverName(), server);
        }
        serverCache = newServerCache;
        plugin.getLogger().info("Updating SurfServer cache completed! Server count: " + servers.size());
    }

    @Override
    public Optional<SurfServer> getServerByName(String serverName) {
        return Optional.ofNullable(serverCache.get(serverName));
    }

    @Override
    public List<SurfServer> getServers() {
        return List.copyOf(serverCache.values());
    }
}
