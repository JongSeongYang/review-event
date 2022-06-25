package com.triple.review.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final ModelMapper customModelMapper = new ModelMapper();

    @Bean
    public ModelMapper modelMapper() {
        customModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return customModelMapper;
    }

}