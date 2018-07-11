package com.estacionamiento.config;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class Config {

//	public static final DateTimeFormatter FORMATTER = ofPattern("dd::MM::yyyy");
//	@Bean
//	@Primary
//	public ObjectMapper serializingObjectMapper() {
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    JavaTimeModule javaTimeModule = new JavaTimeModule();
//	    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
//	    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//	    objectMapper.registerModule(javaTimeModule);
//	    return objectMapper;
//	}
}
