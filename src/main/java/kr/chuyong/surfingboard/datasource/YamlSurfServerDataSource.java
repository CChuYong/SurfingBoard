package kr.chuyong.surfingboard.datasource;

import kr.chuyong.surfingboard.SurfServer;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class YamlSurfServerDataSource implements SurfServerDataSource {
    private final FileConfiguration config;

    @Override
    public void initialize() {
        if (!config.contains("servers")) throw new IllegalStateException("No servers found in config.yml");
    }

    @Override
    public List<SurfServer> loadFromDatabase() {
        List<Map<?, ?>> servers = config.getMapList("servers");
        return servers.stream().map(this::fromMap).toList();
    }

    @Override
    public boolean createNewServer(SurfServer server) {
        List<SurfServer> currentServers = loadFromDatabase();
        boolean isPresent = currentServers.stream()
                .anyMatch(s -> s.serverName().equals(server.serverName()));
        if (isPresent) return false;
        currentServers.add(server);
        config.set("servers", currentServers.stream().map(this::toMap).toList());
        return false;
    }

    @Override
    public boolean updateServer(SurfServer server) {
        List<SurfServer> currentServers = loadFromDatabase();
        Optional<SurfServer> previousServer = currentServers.stream()
                .filter(s -> s.serverName().equals(server.serverName()))
                .findFirst();
        if (previousServer.isEmpty()) return false;
        currentServers.remove(previousServer.get());
        currentServers.add(server);
        config.set("servers", currentServers.stream().map(this::toMap).toList());
        return true;
    }

    @Override
    public boolean deleteServer(SurfServer server) {
        List<SurfServer> currentServers = loadFromDatabase();
        Optional<SurfServer> previousServer = currentServers.stream()
                .filter(s -> s.serverName().equals(server.serverName()))
                .findFirst();
        if (previousServer.isEmpty()) return false;
        currentServers.remove(previousServer.get());
        config.set("servers", currentServers.stream().map(this::toMap).toList());
        return true;
    }

    private SurfServer fromMap(Map<?, ?> map) {
        return new SurfServer(
                (String) map.get("serverName"),
                (String) map.get("hostName"),
                (int) map.get("port")
        );
    }

    private Map<String, Object> toMap(SurfServer server) {
        Map<String, Object> map = new HashMap<>();
        map.put("serverName", server.serverName());
        map.put("hostName", server.hostName());
        map.put("port", server.port());
        return map;
    }
}
