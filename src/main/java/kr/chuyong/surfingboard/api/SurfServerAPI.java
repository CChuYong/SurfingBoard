package kr.chuyong.surfingboard.api;

import kr.chuyong.surfingboard.SurfServer;

import java.util.List;
import java.util.Optional;

public interface SurfServerAPI {
    /**
     * Get SurfServer by server name
     * @param serverName Server name
     * @return SurfServer
     */
    Optional<SurfServer> getServerByName(String serverName);

    /**
     * Get all SurfServers
     * @return SurfServers
     */
    List<SurfServer> getServers();
}
