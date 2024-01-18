package kr.chuyong.surfingboard.command;

import kr.chuyong.surfingboard.SurfServer;
import kr.chuyong.surfingboard.api.SurfServerAPI;
import kr.chuyong.surfingboard.api.SurfingAPI;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SurfCommand implements CommandExecutor, TabCompleter {
    private final SurfServerAPI surfServerAPI;
    private final SurfingAPI surfingAPI;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendMessage(sender, "/surf <playerName> <serverName> - Move player to target server.");
            sendMessage(sender, "/surf all <serverName> - Move everyone in server to target server.");
            sendMessage(sender, "/surf <serverName> - Move yourself to target server.");
            return true;
        }
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("all")) {
                sendWarningMessage(sender, "You must specify server name.");
                return true;
            }

            if(sender instanceof Player player) {
                Optional<SurfServer> surfServer = surfServerAPI.getServerByName(args[0]);
                if (surfServer.isEmpty()) {
                    sendWarningMessage(sender, "Server named " + args[0] + " does not exists.");
                    return true;
                }
                sendMessage(sender, "Teleporting Yourself to " + surfServer.get().serverName());
                surfingAPI.surfToServer(player, surfServer.get());
            } else {
                sendWarningMessage(sender, "You must be a player to use this command.");
            }
        } else {
            String targetPlayerName = args[0];
            Optional<SurfServer> surfServer = surfServerAPI.getServerByName(args[1]);
            if (surfServer.isEmpty()) {
                sendWarningMessage(sender, "Server named " + args[0] + " does not exists.");
                return true;
            }
            if (targetPlayerName.equalsIgnoreCase("all")) {
                sendMessage(sender, "Teleporting Everyone to " + surfServer.get().serverName());
                for(Player player : Bukkit.getOnlinePlayers()) {
                    surfingAPI.surfToServer(player, surfServer.get());
                }
            } else {
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
                if (targetPlayer == null) {
                    sendWarningMessage(sender, "Player named " + targetPlayerName + " does not exists.");
                    return true;
                }
                sendMessage(sender, "Teleporting " + targetPlayer + " to " + surfServer.get().serverName());
                surfingAPI.surfToServer(targetPlayer, surfServer.get());
            }
        }
        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage("§b[S/B] §f" + message);
    }

    private void sendWarningMessage(CommandSender sender, String message) {
        sender.sendMessage("§c[S/B] §f" + message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String prefix = args[0];
            ArrayList<String> tabElements = new ArrayList<>();
            tabElements.add("all");
            tabElements.addAll(surfServerAPI.getServers().stream().map(SurfServer::serverName).toList());
            tabElements.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
            return tabElements.stream().filter(s -> s.startsWith(prefix)).toList();
        } else if(args.length > 1) {
            String prefix = args[1];
            return surfServerAPI.getServers().stream().map(SurfServer::serverName)
                    .filter(s -> s.startsWith(prefix)).toList();
        }
        return null;
    }
}
