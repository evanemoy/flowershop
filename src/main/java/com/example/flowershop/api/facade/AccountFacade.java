package com.example.flowershop.api.facade;

import com.example.flowershop.api.dto.TopUpAccountDTO;
import com.example.flowershop.core.security.SecurityContextHolderUtils;
import com.example.flowershop.core.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@AllArgsConstructor
@Component
public class AccountFacade {
  private final UserService userService;
  public void getUserAccount(Model model){
    Long userId = SecurityContextHolderUtils.getUserId();
    model.addAttribute("user",  userService.findUserById(userId));
  }
  public void setUser(Model model, Long userId){
    model.addAttribute("user", userService.findUserById(userId));
  }
  public void getTopUpPage(Model model, Long userId){
    setUser(model, userId);
    model.addAttribute("topUpAccountDTO", new TopUpAccountDTO(0.0));
  }
  public void topUp(TopUpAccountDTO topUpAccountDTO, Long userId){
    var user = userService.findUserById(userId);
    user.setAccount(user.getAccount()+topUpAccountDTO.getTopUpSum());
    userService.saveUser(user);
  }

}
