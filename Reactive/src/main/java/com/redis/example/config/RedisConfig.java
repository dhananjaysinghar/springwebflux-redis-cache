package com.redis.example.config;

import com.redis.example.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisOperations<String, Product> redisOperations(LettuceConnectionFactory
                                                                            factory) {
        Jackson2JsonRedisSerializer<Product> serializer = new Jackson2JsonRedisSerializer<>(Product.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Product> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Product> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }


    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("127.0.0.1");
        configuration.setPort(6379);
        return new LettuceConnectionFactory(configuration);
    }


//    @Bean
//    public ReactiveRedisTemplate<String, Product> reactiveRedisTemplate(
//            ReactiveRedisConnectionFactory factory) {
//        StringRedisSerializer keySerializer = new StringRedisSerializer();
//
//        Jackson2JsonRedisSerializer<Product> valueSerializer =
//                new Jackson2JsonRedisSerializer<>(Product.class);
//
//        RedisSerializationContext.RedisSerializationContextBuilder<String, Product> builder =
//                RedisSerializationContext.newSerializationContext(keySerializer);
//        RedisSerializationContext<String, Product> context = builder.value(valueSerializer).build();
//        return new ReactiveRedisTemplate<>(factory, context);
//    }

//    @Bean
//    public ReactiveValueOperations<String, Product> reactiveValueOps() {
//        return reactiveRedisTemplate(connectionFactory()).opsForValue();
//    }


}
