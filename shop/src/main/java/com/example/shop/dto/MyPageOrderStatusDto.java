package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
