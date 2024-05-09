package com.example.flowershop.core.repositories;

import com.example.flowershop.core.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long>{
  @Query("select f from Flower f where f.amount>0")
  List<Flower> findAllAvailable();
}
