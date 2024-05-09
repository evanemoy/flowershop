package com.example.flowershop.core.service;

import com.example.flowershop.core.model.Order;
import com.example.flowershop.core.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
  private final OrderRepository orderRepository;
  public void saveOrder(Order order){
    orderRepository.save(order);
  }
  public List<Order> findByUserId(Long userId){
    return orderRepository.findOrdersByUserId(userId);
  }
}
