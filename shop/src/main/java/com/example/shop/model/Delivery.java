package com.example.shop.model;

import com.example.shop.model.type.DeliveryStatus;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Delivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private UserAddress userAddress;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

}
