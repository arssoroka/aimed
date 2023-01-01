package com.example.springbootappwithh2database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="producttable")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id ;
     private String name;
     private double price;

}
