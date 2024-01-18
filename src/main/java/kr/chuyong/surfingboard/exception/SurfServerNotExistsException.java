package kr.chuyong.surfingboard.exception;

public class SurfServerNotExistsException extends SurfingException {
    public SurfServerNotExistsException(String serverName) {
        super("Surfing server named " + serverName + " not exists.");
    }
}
