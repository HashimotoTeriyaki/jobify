package com.webwizard.apigateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class Routes {

    @Value("${jobOfferService.port}")
    private String port;
    private static final String HOST = "http://localhost:";

    @Bean
    public RouterFunction<ServerResponse> jobOfferService() {
        return GatewayRouterFunctions.route("job_offer_service")
                .route(path("job-offer"),
                        http(HOST + port))
                .route(path("/job-offer/{id}"),
                        http(HOST + port))
                .build();
    }
}
