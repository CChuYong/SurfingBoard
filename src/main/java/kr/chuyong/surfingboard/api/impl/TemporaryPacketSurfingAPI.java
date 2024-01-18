package kr.chuyong.surfingboard.api.impl;

import kr.chuyong.surfingboard.SurfServer;
import kr.chuyong.surfingboard.api.SurfServerAPI;
import kr.chuyong.surfingboard.api.SurfingAPI;
import kr.chuyong.surfingboard.exception.SurfServerNotExistsException;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Optional;

@RequiredArgsConstructor
public class TemporaryPacketSurfingAPI implements SurfingAPI {
    private final SurfServerAPI surfServerAPI;

    @Override
    public void surfToServer(Player player, String serverName) {
        Optional<SurfServer> targetServer = surfServerAPI.getServerByName(serverName);
        if (targetServer.isEmpty()) throw new SurfServerNotExistsException(serverName);
        this.surfToServer(player, targetServer.get());
    }

    @Override
    public void surfToServer(Player player, SurfServer targetServer) {

    }
}
