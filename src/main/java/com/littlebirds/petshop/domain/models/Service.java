package com.littlebirds.petshop.domain.models;

import com.littlebirds.petshop.domain.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "services")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Promotion> promotions = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal price;

    public void applyDiscount(Promotion promotion) {
        if (promotion != null && promotion.getIsActive()) {
            BigDecimal discount = BigDecimal.valueOf(promotion.getDiscount()).divide(BigDecimal.valueOf(100));
            BigDecimal discountedPrice = this.price.subtract(this.price.multiply(discount));
            this.price = discountedPrice;
        }
    }

    public void applyBestDiscount() {
        Promotion bestPromotion = promotions.stream()
                .filter(Promotion::getIsActive)
                .max(Comparator.comparingInt(Promotion::getDiscount))
                .orElse(null);

        if (bestPromotion != null) {
            applyDiscount(bestPromotion);
        }
    }
}
