package com.OrderService.OrderService.repository;

import com.OrderService.OrderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order,Integer> {

}
