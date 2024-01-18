package kr.chuyong.surfingboard.api;

import kr.chuyong.surfingboard.SurfServer;

import java.util.List;
import java.util.Optional;

public interface SurfServerAPI {
    // Read
    Optional<SurfServer> getServerByName(String serverName);
    List<SurfServer> getServers();
}
