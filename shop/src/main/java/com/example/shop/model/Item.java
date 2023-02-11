package com.example.shop.model;

import com.example.shop.exception.NotEnoughStackException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String firstCategory;

    private String secondCategory;

    private String thirdCategory;

    private String itemName;

    private int price;

    @Column(columnDefinition = "TEXT")
    private String itemInfo;

    private String color;

    private String favric;

    private String model;

    private String size;

    private int stackQuantity;

    @Column(columnDefinition = "TEXT")
    private String imgUrl;

    private String saleStatus;

    private Long itemIdx;

    private boolean rep;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();

    public void plusStackQuantity(int plusQuantity) {
        this.stackQuantity += plusQuantity;
    }

    public void minusStackQuantity(int minusQuantity){
        int resultStack = this.stackQuantity - minusQuantity;
        if(resultStack < 0) {
            throw new NotEnoughStackException("재고가 없습니다.");
        }else {
            this.stackQuantity = resultStack;
        }
    }
}
