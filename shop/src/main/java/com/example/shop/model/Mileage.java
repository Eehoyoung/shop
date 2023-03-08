package com.example.shop.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Mileage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mileage_id")
    private Long id;

    private int mileagePrice;

    private String mileageContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //연관 관계 메소드
    public void setUser(User user) {
        this.user = user;
        user.getMileageList().add(this);
    }
}
