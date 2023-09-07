package com.csaa.envdashboard;

import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtlpConfiguration {

    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url,
                                              @Value("${tracing.authCode}") String authCode) {
        System.out.println("OTLP Configuration 1");
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .addHeader("x-honeycomb-team", authCode)
                .build();
    }
}
