package com.main.SatellitePathPredictionSpringBoot.Others;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TLESender {



	public static void main(String[] args) {

//		String filePath = "ISS_ZARYA.tle";
//		String filePath = "ISS_ZARYA2.tle";
//		String filePath = "ISS_ZARYA3.tle";
		String filePath = "active.tle";

		try {
			// Read file content
			String tleContent = Files.readString(Paths.get(filePath)); 

			System.out.println("TLECONTENT " + tleContent);

			// RabbitMQ setup
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");

			try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
//				channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
//				channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, tleContent.getBytes());
				channel.queueDeclare("SATELLITE_TLE_QUEUE", true, false, false, null);
				
				channel.basicPublish("", "SATELLITE_TLE_QUEUE", null, tleContent.getBytes());
				System.out.println("✅ TLE file sent to RabbitMQ.");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
