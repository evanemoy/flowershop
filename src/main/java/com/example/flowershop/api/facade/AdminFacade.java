package com.example.flowershop.api.facade;


import com.example.flowershop.api.dto.ConfirmAdminDTO;
import com.example.flowershop.api.dto.UpdateAmountDTO;
import com.example.flowershop.core.exception.NotFoundException;
import com.example.flowershop.core.model.Flower;
import com.example.flowershop.core.model.Role;
import com.example.flowershop.core.security.SecurityContextHolderUtils;
import com.example.flowershop.core.service.FlowerService;
import com.example.flowershop.core.service.PhotoService;
import com.example.flowershop.core.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Component
@PropertySource("classpath:application.yml")
public class AdminFacade {
  private final FlowerService flowerService;
  private final UserService userService;
  private final PhotoService photoService;
  private final String secret = "secretpassword";

  public void getConfirmPage(Model model) {
    model.addAttribute("confirmAdminDTO", new ConfirmAdminDTO());
  }

  public void confirmAdmin(String confirmKey, Model model) {
    Long userId = SecurityContextHolderUtils.getUserId();
    if (confirmKey.equals(secret)) {
      var user = userService.findUserById(userId);
      user.setRole(Role.ADMIN);
      userService.saveUser(user);
      SecurityContextHolderUtils.updateRole(Role.ADMIN);
    } else {
      model.addAttribute("errorConfirming", "Wrong confirm code");
    }
  }

  public void getFlowersPage(Model model){
    model.addAttribute("flowers", flowerService.getFlowers());
  }

  public void getCreateFlowerPage(Model model) {
    model.addAttribute("flower", new Flower());
  }

  public void createFlower(Flower flower, MultipartFile file) throws Exception{
    flower.setPhotoName(photoService.savePhoto(file));
    flowerService.saveFlower(flower);
  }

  public void updateAmount(Long flowerId, UpdateAmountDTO dto){
    Flower flower = flowerService.findById(flowerId);
    flower.setAmount(flower.getAmount()+dto.getAmount());
    flowerService.saveFlower(flower);
  }


  public void getUpdateFlowerAmountPage(Model model, Long flowerId) {
    model.addAttribute("flowerId", flowerId);
    model.addAttribute("updateAmountDTO", new UpdateAmountDTO());
  }

}
