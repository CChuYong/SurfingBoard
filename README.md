# SurfingBoard
Easily surf between servers via Minecraft 1.20.5 new packet feature: Transfer!  
This plugin will store your server lists and allow you to surf between servers easily. 
Also, this plugin provides MySQL DataSource to share and sync your server lists across the different servers.

Currently, there's no any server frameworks (Spigot, Paper) released on 1.20.5, 
so this plugin cannot actually send between servers until the server frameworks are released.  
But until release, you can still use this plugin to store your server lists and share them across the servers.

## Commands
### Surfing Commands (requires `surfingboard.command.surf` permission)
- `/surf <playerName> <serverName>`: Send the player to the server.
- `/surf all <serverName>`: Send everyone to the server.
- `/surf <serverName>`: Send yourself to target server.

## Developer API
### Importing SurfingBoard API
```java
import kr.chuyong.surfingboard.SurfingBoard;
import kr.chuyong.surfingboard.api.SurfingAPI;
import kr.chuyong.surfingboard.api.SurfServerAPI;

//Surfing APIs (Move Players)
SurfingAPI surfingAPI = SurfingBoard.getSurfingAPI();

//Surfing Server APIs (Manage Servers)
SurfServerAPI surfServerAPI = SurfingBoard.getSurfServerAPI();
```

### (i.e. Move Players to server)
```java
public void myMovePlayerMethod(Player player, String serverName){
    SurfingAPI surfingAPI = SurfingBoard.getSurfingAPI();
    surfingAPI.movePlayer(player, serverName);
}
```
