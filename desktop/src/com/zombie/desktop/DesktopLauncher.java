package com.zombie.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zombie.api.CabBookingService;
import com.zombie.client.Main;
import com.zombie.client.utils.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.util.SocketUtils;

@ComponentScan
@EnableAutoConfiguration
@Configuration
public class DesktopLauncher implements EmbeddedServletContainerCustomizer {
	@Bean
	public HessianProxyFactoryBean hessianInvoker() {
		HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
		invoker.setServiceUrl("http://localhost:8032/booking");
		invoker.setServiceInterface(CabBookingService.class);
		return invoker;
	}
	public static void main (String[] arg) {
		CabBookingService service = SpringApplication.run(DesktopLauncher.class).getBean(CabBookingService.class);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Zombie Defense";
		config.width=Constants.GAME_WIDTH;
		config.height=Constants.GAME_HEIGHT;
		config.forceExit = true;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(service), config);
	}
	@Override
	public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
		int freePort = SocketUtils.findAvailableTcpPort();
		configurableEmbeddedServletContainer.setPort(freePort);
	}
}
