package org.example.primes.apigateway;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.primes.apigateway.service.PrimesGrpcClientWrapper;
import org.example.primes.apigateway.validation.NumberValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;

@Configuration
@EnableWebFlux
public class Config implements WebFluxConfigurer {

    @Value("${grpc.host}")
    private String grpcHost;

    @Value("${grpc.port}")
    private Integer grpcPort;

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    @Bean
    public PrimesGrpcClientWrapper primesGrpcClient(ManagedChannel managedChannel) {
        return new PrimesGrpcClientWrapper(managedChannel);
    }

    @Bean
    public NumberValidator numberValidator() {
        return new NumberValidator();
    }

    @Bean
    public WebFluxResponseStatusExceptionHandler webFluxResponseStatusExceptionHandler() {
        return new WebFluxResponseStatusExceptionHandler();
    }
}