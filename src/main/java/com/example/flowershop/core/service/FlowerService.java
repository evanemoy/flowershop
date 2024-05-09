package com.example.flowershop.core.service;


import com.example.flowershop.core.exception.NotFoundException;
import com.example.flowershop.core.model.Flower;
import com.example.flowershop.core.repositories.FlowerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FlowerService {
  private final FlowerRepository flowerRepository;

  public List<Flower> getFlowers() {
    return flowerRepository.findAll();
  }

  public List<Flower> getFlowersAvailable(){
    return flowerRepository.findAllAvailable();
  }

  public void saveFlower(Flower flower) {
    flowerRepository.save(flower);
  }

  public Flower findById(Long flowerId) {
    return flowerRepository.findById(flowerId).orElseThrow(() -> new NotFoundException("Error getting flower"));
  }

  public void deleteById(Long flowerId) {
    flowerRepository.deleteById(flowerId);
  }

}
