package com.example.flowershop.api.facade;


import com.example.flowershop.api.dto.BuyFlowerDTO;
import com.example.flowershop.core.exception.LessMoneyException;
import com.example.flowershop.core.exception.NotEnoughFlowers;
import com.example.flowershop.core.model.Flower;
import com.example.flowershop.core.model.Order;
import com.example.flowershop.core.model.User;
import com.example.flowershop.core.security.SecurityContextHolderUtils;
import com.example.flowershop.core.service.FlowerService;
import com.example.flowershop.core.service.OrderService;
import com.example.flowershop.core.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@AllArgsConstructor
@Component
public class ProductsFacade {
  private final FlowerService flowerService;
  private final OrderService orderService;
  private final UserService userService;

  public void getProductsPage(Model model) {
    model.addAttribute("flowers", flowerService.getFlowersAvailable());
  }

  public void getBuyPage(Model model, Long flowerId) {
    model.addAttribute("flowerId", flowerId);
    model.addAttribute("buyDTO", new BuyFlowerDTO());
  }

  public void buyProduct(Long flowerId, BuyFlowerDTO dto) throws NotEnoughFlowers {
    Flower flower = flowerService.findById(flowerId);
    User user = userService.findUserById(SecurityContextHolderUtils.getUserId());
    checkEnoughMoney(user, flower, dto);
    checkEnoughAmount(flower, dto);
    user.setAccount(user.getAccount()-dto.getAmount()*flower.getPrice());
    flower.setAmount(flower.getAmount() - dto.getAmount());
    var order = createOrder(dto, user, flower);
    flower.getOrders().add(order);
    user.getOrders().add(order);
    userService.saveUser(user);
    flowerService.saveFlower(flower);
    orderService.saveOrder(order);
  }

  private void checkEnoughMoney(User user, Flower flower, BuyFlowerDTO dto) {
    if (user.getAccount() < flower.getPrice() * dto.getAmount()) {
      throw new LessMoneyException("Less money for buying such amount of flowers");
    }
  }

  private void checkEnoughAmount(Flower flower, BuyFlowerDTO dto){
    if (flower.getAmount() < dto.getAmount()) {
      throw new NotEnoughFlowers("Shop hasn't enough flowers to do this order");
    }
  }

  private Order createOrder(BuyFlowerDTO dto, User user, Flower flower){
    Order order = new Order();
    order.setAmount(dto.getAmount());
    order.setUser(user);
    order.setFlower(flower);
    return order;
  }
}
