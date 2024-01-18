package kr.chuyong.surfingboard.api;

import kr.chuyong.surfingboard.SurfServer;
import org.bukkit.entity.Player;

public interface SurfingAPI {
    /**
     * Surf to server
     * @param player Target Player
     * @param serverName Server name
     */
    void surfToServer(Player player, String serverName);

    /**
     * Surf to server
     * @param player Target Player
     * @param targetServer Target Server
     */
    void surfToServer(Player player, SurfServer targetServer);
}
