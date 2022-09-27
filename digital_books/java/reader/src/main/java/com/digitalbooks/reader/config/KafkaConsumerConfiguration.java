package com.digitalbooks.reader.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.digitalbooks.common.models.NotificationPayload;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	@Bean
	public ConsumerFactory<String, NotificationPayload> userConsumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
				new JsonDeserializer<>(NotificationPayload.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, NotificationPayload> userKafkaListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, NotificationPayload> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}

}
