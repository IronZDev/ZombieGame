package com.zombie.server;


import com.zombie.api.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;

import java.util.Properties;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Server {
    @Bean
    GameService gameService() {
        return new GameServiceImpl();
    }

    @Bean(name = "/game")
    RemoteExporter hessianService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(gameService());
        exporter.setServiceInterface(GameService.class);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Server.class);
        Properties properties = new Properties();
        properties.put("server.port", "8032");
        app.setDefaultProperties(properties);
        app.run(args);
    }

}