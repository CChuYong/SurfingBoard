package kr.chuyong.surfingboard.datasource;

import kr.chuyong.surfingboard.SurfServer;
import kr.chuyong.surfingboard.api.SurfServerAPI;

import java.util.List;

public interface SurfServerDataSource {
    void initialize();
    List<SurfServer> loadFromDatabase();
    boolean createNewServer(SurfServer server);
    boolean updateServer(SurfServer server);
    boolean deleteServer(SurfServer server);
}
