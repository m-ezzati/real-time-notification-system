package com.saber.testHibernate.client;

import com.saber.testHibernate.config.SimulatorApiProperties;
import com.saber.testHibernate.exception.SimulatorServiceUnavailableException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.Set;

/**
 * @author M.Ezati
 * 12/05/2026
 */
@Service
public class SimulatorClient {
    private final WebClient webClient;
    private final SimulatorApiProperties apiProperties;

    public SimulatorClient(WebClient webClient, SimulatorApiProperties apiProperties) {
        this.webClient = webClient;
        this.apiProperties = apiProperties;
    }

    public Set<String> getSymbols() {
        try {
            Set<String> symbols = webClient.get()
                    .uri(apiProperties.getSymbolsPath())
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Set<String>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
            if (symbols == null) {
                throw new SimulatorServiceUnavailableException("Simulator service returned empty response");
            }
            return symbols;
        } catch (WebClientRequestException ex) {
            throw new SimulatorServiceUnavailableException("Simulator service is unavailable or unreachable", ex);
        } catch (WebClientResponseException ex) {
            throw new SimulatorServiceUnavailableException("Simulator service returned invalid response. status=" + ex.getStatusCode().value(), ex);
        } catch (Exception ex) {
            throw new SimulatorServiceUnavailableException("Failed to fetch symbols from simulator service", ex);
        }
    }
}