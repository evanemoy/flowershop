package com.example.flowershop.api.facade;

import com.example.flowershop.core.security.SecurityContextHolderUtils;
import com.example.flowershop.core.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@AllArgsConstructor
@Component
public class OrderFacade {
  private final OrderService orderService;
  public void getOrdersPage(Model model){
    var orders = orderService.findByUserId(SecurityContextHolderUtils.getUserId());
    model.addAttribute("orders", orders);
  }
}
