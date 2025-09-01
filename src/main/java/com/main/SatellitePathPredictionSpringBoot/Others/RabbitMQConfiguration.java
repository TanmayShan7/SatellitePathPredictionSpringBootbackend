package com.main.SatellitePathPredictionSpringBoot.Others;



import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {

    public static final String QUEUE_NAME = "SATELLITE_TLE_QUEUE";

    @Bean
    public Queue createSatelliteTLEQueue() {
		return new Queue(QUEUE_NAME, true);
    		
    }    
}
