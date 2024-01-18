package kr.chuyong.surfingboard.datasource;

import kr.chuyong.surfingboard.SurfServer;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLSurfServerDataSource implements SurfServerDataSource {
    private final ConfigurationSection section;
    private Connection dataSource;

    public MySQLSurfServerDataSource(ConfigurationSection section) {
        this.section = section;
    }

    @Override
    public void initialize() {
        String host = section.getString("host");
        int port = section.getInt("port");
        String database = section.getString("database");
        String username = section.getString("username");
        String password = section.getString("password");
        try {
            this.dataSource = DriverManager
                    .getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true",
                            username, password);

            try(PreparedStatement statement = dataSource.prepareStatement("CREATE TABLE IF NOT EXISTS surfing_servers(" +
                    "server_name VARCHAR(255) PRIMARY KEY," +
                    "host_name VARCHAR(255) NOT NULL," +
                    "port INT NOT NULL" +
                    ")")){
                statement.execute();
            }
        }catch(SQLException ex) {
            throw new RuntimeException("SQL Connection failed", ex);
        }
    }

    @Override
    public List<SurfServer> loadFromDatabase() {
        List<SurfServer> servers = new ArrayList<>();
        try (PreparedStatement stmt = dataSource.prepareStatement("SELECT * FROM surfing_servers");
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                servers.add(
                        new SurfServer(
                                rs.getString("server_name"),
                                rs.getString("host_name"),
                                rs.getInt("port")
                        )
                );
            }

        } catch (SQLException ex) {
            throw new RuntimeException("SQL Query failed", ex);
        }
        return servers;
    }

    @Override
    public boolean createNewServer(SurfServer server) {
        try(PreparedStatement stmt = dataSource.prepareStatement("INSERT INTO surfing_servers(server_name, host_name, port) VALUES(?, ?, ?)")) {
            stmt.setString(1, server.serverName());
            stmt.setString(2, server.hostName());
            stmt.setInt(3, server.port());
            return stmt.executeUpdate() > 0;
        } catch(SQLException ex){
            throw new RuntimeException("SQL Query failed", ex);
        }
    }

    @Override
    public boolean updateServer(SurfServer server) {
        try(PreparedStatement stmt = dataSource.prepareStatement("UPDATE surfing_servers SET host_name=?, port=? WHERE server_name=?")) {
            stmt.setString(1, server.hostName());
            stmt.setInt(2, server.port());
            stmt.setString(3, server.serverName());
            return stmt.executeUpdate() > 0;
        } catch(SQLException ex){
            throw new RuntimeException("SQL Query failed", ex);
        }
    }

    @Override
    public boolean deleteServer(SurfServer server) {
        try(PreparedStatement stmt = dataSource.prepareStatement("DELETE FROM surfing_servers WHERE server_name=?")) {
            stmt.setString(1, server.serverName());
            return stmt.executeUpdate() > 0;
        } catch(SQLException ex){
            throw new RuntimeException("SQL Query failed", ex);
        }
    }
}
