# SurfingBoard
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html) [![Maven Central](https://img.shields.io/maven-central/v/kr.chuyong/surfingboard.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/kr.chuyong/surfingboard.svg)  
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

## Configuration
```yaml
# Datasource Configuration
# Available datasource: MySQL, YAML
datasource: YAML

# MySQL Datasource Configuration
# Not used if datasource is YAML
mysql:
  host: localhost
  port: 3306
  database: minecraft
  username: root
  password: 1234

#YAML Datasource Configuration
# Not used if datasource is MySQL
servers:
  - serverName: myServerName1
    hostName: localhost
    port: 25566
  - serverName: myServerName2
    hostName: localhost
    port: 25567

# Plugin Configuration
#  serverUpdatePeriod: Time to update server information from datasource
surfing:
  serverUpdatePeriod: 1200 # 1 minute (based on Tick). -1 to Disable auto update
```

## Developer API
### Importing SurfingBoard API
As SurfingBoard uploaded to Maven Central, you could easily import from gradle/maven.
```
dependencies {
  implementation 'kr.chuyong:surfingboard:1.0.0'
}
```

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
