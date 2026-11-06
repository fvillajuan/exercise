package com.demo.exercise.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.tomcat.TomcatWebServerFactory.DEFAULT_PROTOCOL;

@Configuration(proxyBeanMethods = false)
public class TomcatServerConfig {

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> connectorCustomizer() {
		return (TomcatServletWebServerFactory tomcat) ->
				tomcat.addAdditionalConnectors(creerHttpConnecteur());
	}

	private Connector creerHttpConnecteur() {
		Connector connecteur = new Connector(DEFAULT_PROTOCOL);
		connecteur.setScheme("http");
		connecteur.setPort(9000);
		connecteur.setSecure(false);
		connecteur.setRedirectPort(9443);
		return connecteur;
	}
}