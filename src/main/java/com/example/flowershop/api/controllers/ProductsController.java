package com.example.flowershop.api.controllers;

import com.example.flowershop.api.dto.BuyFlowerDTO;
import com.example.flowershop.api.facade.ProductsFacade;
import com.example.flowershop.core.exception.NotEnoughFlowers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductsController {
  private final ProductsFacade productsFacade;
  @GetMapping
  public String getProductsPage(Model model){
    productsFacade.getProductsPage(model);
    return "products/products";
  }
  @GetMapping("/buy/{flowerId}")
  public String getBuyPage(@PathVariable("flowerId") Long flowerId, Model model){
    productsFacade.getBuyPage(model, flowerId);
    return "products/buy";
  }
  @PostMapping("/buy/{flowerId}")
  public String buyFlower(Model model,
                          @PathVariable("flowerId") Long flowerId,
                          @ModelAttribute("buyDTO")BuyFlowerDTO dto,
                          BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      return "products/buy";
    }
    try{
      productsFacade.buyProduct(flowerId, dto);
    }catch (NotEnoughFlowers e){
      model.addAttribute("error", "Не хватает цветов для осуществления заказа");
      return "products/buy";
    }
    return "redirect:/products/products";
  }
}

