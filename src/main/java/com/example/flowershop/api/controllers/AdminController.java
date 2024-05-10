package com.example.flowershop.api.controllers;


import com.example.flowershop.api.dto.ConfirmAdminDTO;
import com.example.flowershop.api.dto.UpdateAmountDTO;
import com.example.flowershop.api.facade.AdminFacade;
import com.example.flowershop.core.model.Flower;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
  private final AdminFacade adminFacade;

  @GetMapping("/confirm")
  public String getConfirmPage(Model model) {
    adminFacade.getConfirmPage(model);
    return "admin/admin_confirm";
  }

  @PostMapping("/confirm")
  public String confirmAdmin(Model model,
                             @ModelAttribute("confirmAdminDTO") @Valid ConfirmAdminDTO confirmAdminDTO,
                             BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "admin/admin_confirm";
    }
    adminFacade.confirmAdmin(confirmAdminDTO.getConfirmKey(), model);
    if (model.getAttribute("errorConfirming") != null) {
      return "admin/admin_confirm";
    } else {
      return "redirect:/admin";
    }
  }

  @GetMapping
  public String getMainPage() {
    return "admin/admin";
  }

  @GetMapping("/flowers")
  public String getFlowersPage(Model model) {
    adminFacade.getFlowersPage(model);
    return "admin/admin_flowers";
  }

  @GetMapping("/flowers/create")
  public String getCreateFlowerPage(Model model) {
    adminFacade.getCreateFlowerPage(model);
    return "admin/admin_create_flower";
  }

  @PostMapping("/flowers/create")
  public String createFlower(@RequestParam("image") MultipartFile image,
                             @ModelAttribute("flower") @Valid Flower flower,
                             BindingResult bindingResult) throws Exception {
    if (bindingResult.hasErrors()) {
      return "admin/admin_create_flower";
    }
    adminFacade.createFlower(flower, image);
    return "redirect:/admin/flowers";
  }

  @GetMapping("/flowers/update-amount/{flowerId}")
  public String getUpdateFlowerAmountPage(@PathVariable("flowerId") Long flowerId, Model model) {
    adminFacade.getUpdateFlowerAmountPage(model, flowerId);
    return "admin/admin_update_flower";
  }

  @PatchMapping("/flowers/update-amount/{flowerId}")
  public String updateAmount(@PathVariable("flowerId") Long flowerId,
                             @ModelAttribute("updateAmountDTO") @Valid UpdateAmountDTO dto,
                             BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "admin/admin_update_flower";
    }
    adminFacade.updateAmount(flowerId, dto);
    return "redirect:/admin/flowers";
  }


}