package com.example.shop.model;

import com.example.shop.model.type.UserGrade;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String sex;

    private String email;

    private String birthday;

    @Enumerated(EnumType.STRING)
    private UserGrade userGrade;

    @Embedded
    private UserAddress userAddress;

    private int visitCount;

    private int orderCount;

    private String phoneNumber;

    private String homePhoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Mileage> mileageList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DeliveryAddress> deliveryAddressList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();

    public User(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

    @Builder
    public User(Long id, String loginId, String password, String name, String homePhoneNumber, String phoneNumber, String email, String birthday) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.homePhoneNumber = homePhoneNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }


}
