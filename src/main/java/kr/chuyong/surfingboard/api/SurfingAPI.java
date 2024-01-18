package kr.chuyong.surfingboard.api;

import kr.chuyong.surfingboard.SurfServer;
import org.bukkit.entity.Player;

public interface SurfingAPI {
    void surfToServer(Player player, String serverName);
    void surfToServer(Player player, SurfServer targetServer);
}
