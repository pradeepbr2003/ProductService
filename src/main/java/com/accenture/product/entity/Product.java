package com.accenture.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @Column
    private String name;

    @Column
    private double price;
}
