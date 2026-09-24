package com.saber.testHibernate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author M.Ezati
 * 12/05/2026
 */
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient simulatorWebClient(SimulatorApiProperties props) {
        return WebClient.builder()
                .baseUrl(props.getBaseUrl())
                .build();
    }
}