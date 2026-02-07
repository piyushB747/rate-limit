package net.kanth.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigWeb {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
