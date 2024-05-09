package com.example.flowershop.core.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "flowers")
public class Flower {
  @Id
  @Column(name = "flower_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long flowerId;
  @NotBlank
  private String description;
  @Min(1)
  private Long price;
  @Min(0)
  private Integer amount;
  @Column(name = "photo_name")
  private String photoName;
  @OneToMany(mappedBy = "flower")
  private List<Order> orders;
}
