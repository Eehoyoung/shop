package com.example.shop.model;

import com.example.shop.exception.DeliveryException;
import com.example.shop.model.type.DeliveryStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private LocalDate orderedAt;

    private String payment;

    private int totalPrice;

    @ColumnDefault("100")
    private int usedMileagePrice;

    // 생성 메소드
    public static Order createOrder(User user, Delivery delivery, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setDelivery(delivery);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setOrderedAt(LocalDate.now());
        order.setPayment("카드결제");
        order.setTotalPrice(order.getCalTotalPrice());

        return order;
    }

    public void setUser(User user) {
        this.user = user;
        user.getOrderList().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    /* 비즈니스 로직 */

    public void orderCancel() {
        if (this.delivery.getDeliveryStatus() == DeliveryStatus.COMPLETE) {
            throw new DeliveryException("이미 배송완료된 상품입니다.");
        } else {
//            this.setOrderStatus(OrderStatus.CANCEL);
            this.delivery.setDeliveryStatus(DeliveryStatus.CANCEL);

            for (OrderItem orderItem : orderItemList) {
                orderItem.itemCancel();
            }
        }
    }// 주문 취소

    public int getCalTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getCalPrice();
        }
        return totalPrice;
    }// 전체 가격 조회
}
