package com.ecommerce.Security;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class ScalarConfig {

    @Bean
    GraphQLScalarType nonNegativeInt(){
        return ExtendedScalars.NonNegativeInt;
    }
    @Bean
    public GraphQLScalarType dateTime(){
        return ExtendedScalars.DateTime;
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.DateTime);
    }
}
