package com.wbsrisktaskerx.wbsrisktaskerx.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private CarBrand brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "version_name", nullable = false)
    private String versionName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CarCategory category;

    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
