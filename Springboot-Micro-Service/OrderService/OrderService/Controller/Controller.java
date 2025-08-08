package com.OrderService.OrderService.Controller;


import com.OrderService.OrderService.dto.OrderResponse;
import com.OrderService.OrderService.dto.ProductDto;
import com.OrderService.OrderService.model.Order;
import com.OrderService.OrderService.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/order-service")
public class Controller {

    private WebClient.Builder webClient;
    private OrderRepository orderRepository;

    public Controller(WebClient.Builder webClient, OrderRepository orderRepository) {
        this.webClient = webClient;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @PostMapping("/placeOrder")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Order order) {

        return webClient.build().get().uri("http://product-service/product/{id}", order.getProductId())
                .retrieve()
                .bodyToMono(ProductDto.class)
                .map(productDto -> {
                    orderRepository.save(order);
                    OrderResponse orderResponse = new OrderResponse();
                    orderResponse.setId(order.getId());
                    orderResponse.setProduct_id(productDto.getId());
                    orderResponse.setQuantity(order.getQuantity());
                    orderResponse.setProductName(productDto.getName());
                    orderResponse.setPrice(productDto.getPrice());
                    orderResponse.setTotal_price(productDto.getPrice() * order.getQuantity());
                    return ResponseEntity.ok(orderResponse);
                })
                .onErrorResume(ex -> {
                    ex.printStackTrace(); // Log the exception, replace with a logger in production
                    return Mono.just(ResponseEntity.status(500).body(null));
                });

    }
}

