package com.example.shop.model;


import com.example.shop.model.type.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice * count);
        orderItem.setCount(count);

        item.minusStackQuantity(count);

        return orderItem;
    }

    public void setItem(Item item) {
        this.item = item;
        item.getOrderItemList().add(this);
    }

    //비즈니스 로직
    public void itemCancel() {
        this.getItem().plusStackQuantity(count);
    } //주문 취소 시 물량 복구

    public int getCalPrice() {
        return this.getOrderPrice() * this.getCount();
    } // 전체 가격 조회
}
