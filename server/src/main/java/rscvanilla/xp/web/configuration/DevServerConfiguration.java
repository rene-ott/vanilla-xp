package rscvanilla.xp.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DevServerConfiguration {

    @Value("${dev_server.port}")
    private int port;

    @Value("${dev_server.host}")
    private String host;

    public String getAddress() {
        return String.format("%s:%d", host, port);
    }
}
