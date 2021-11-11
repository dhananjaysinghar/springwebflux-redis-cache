package com.redis.example.controller;

import com.redis.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ReactiveRedisOperations<String, Product> redisOperations;

    @PostMapping
    public Mono<Product> save(@RequestBody Product product) {
        return redisOperations.opsForValue().set(product.getId(), product)
                .flatMap(e -> redisOperations.opsForValue().get(product.getId()));
    }


    @GetMapping
    public Flux<Product> getAll() {
        return redisOperations.keys("*")
                .flatMap(redisOperations.opsForValue()::get);
    }

    @GetMapping("/{id}")
    public Mono<Product> findProduct(@PathVariable String id) {
        return redisOperations.opsForValue().get(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> remove(@PathVariable String id) {
        return redisOperations.opsForValue().delete(id);
    }


}
