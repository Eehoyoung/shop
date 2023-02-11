package com.example.shop.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyPageOrderStatusDto {
    private int payWaitingNum;
    private int preShipNum;
    private int inShipNum;
    private int completeShipNum;
    private int orderCancelNum;
    private int orderChangeNum;
    private int orderRefundNum;

}
