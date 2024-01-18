package kr.chuyong.surfingboard.api.impl;

import kr.chuyong.surfingboard.SurfServer;
import kr.chuyong.surfingboard.api.SurfServerAPI;
import kr.chuyong.surfingboard.api.SurfingAPI;
import kr.chuyong.surfingboard.exception.SurfServerNotExistsException;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

@RequiredArgsConstructor
public class MockedPacketSurfingAPI implements SurfingAPI {
    private final SurfServerAPI surfServerAPI;

    @Override
    public void surfToServer(Player player, String serverName) {
        Optional<SurfServer> targetServer = surfServerAPI.getServerByName(serverName);
        if (targetServer.isEmpty()) throw new SurfServerNotExistsException(serverName);
        this.surfToServer(player, targetServer.get());
    }

    @Override
    public void surfToServer(Player player, SurfServer targetServer) {
        player.sendMessage(ChatColor.RED + "[SurfingBoard] Currently, SurfingBoard does not support Teleport between servers.");
        player.sendMessage(ChatColor.RED + "[SurfingBoard] As 1.20.5 Release, We will update this feature.");
        player.sendMessage(ChatColor.RED + "[SurfingBoard] Until that, Keep using this plugin to store server information!");
        player.sendMessage(ChatColor.RED + "[SurfingBoard] ");
        player.sendMessage(ChatColor.RED + "[SurfingBoard] Your Stored Target Server Information:");
        player.sendMessage(ChatColor.RED + "[SurfingBoard] serverName: " + targetServer.serverName());
        player.sendMessage(ChatColor.RED + "[SurfingBoard] hostName: " + targetServer.hostName());
        player.sendMessage(ChatColor.RED + "[SurfingBoard] port: " + targetServer.port());
    }
}
