package com.product.management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "entry_date")
    private LocalDate entry_date;

    @Column(name = "item_code")
    @JsonProperty("itemCode")
    private long itemCode;

    @Column(name = "item_name")
    @JsonProperty("itemName")
    private String itemName;

    @Column(name = "item_quantity")
    @JsonProperty("itemQuantity")
    private int itemQuantity;

    @Column(name = "upc")
    private String upc;

    @Column(name = "ean")
    private String ean;

    @Column(name = "sku")
    private String sku;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "mpc")
    private String mpc;

    @Column(name = "s_status")
    @JsonProperty("sStatus")
    private String sStatus;

}
