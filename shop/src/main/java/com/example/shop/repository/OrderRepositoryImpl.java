package com.example.shop.repository;

import com.example.shop.dto.MainPageOrderDto;
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.QMainPageOrderDto;
import com.example.shop.dto.QOrderDto;
import com.example.shop.model.QOrder;
import com.example.shop.model.QOrderItem;
import com.example.shop.model.QUser;
import com.example.shop.model.SearchOrder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderDto> searchAllOrder(Pageable pageable) {
        QueryResults<OrderDto> results = queryFactory
                .select(new QOrderDto(
                        QOrder.order.id,
                        QOrderItem.orderItem.id,
                        QUser.user.name,
                        QOrderItem.orderItem.item.itemName,
                        QOrder.order.orderedAt,
                        QOrder.order.payment,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.orderStatus
                ))
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem).on(QOrderItem.orderItem.eq(QOrder.order.orderItemList.any()))
                .leftJoin(QUser.user).on(QUser.user.eq(QOrder.order.user))
                .orderBy(QOrderItem.orderItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<OrderDto> searchAllOrderByCondition(SearchOrder searchOrder, Pageable pageable) {
        QueryResults<OrderDto> results = queryFactory
                .select(new QOrderDto(
                        QOrder.order.id,
                        QOrderItem.orderItem.id,
                        QUser.user.name,
                        QOrderItem.orderItem.item.itemName,
                        QOrder.order.orderedAt,
                        QOrder.order.payment,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.orderStatus
                ))
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem).on(QOrderItem.orderItem.eq(QOrder.order.orderItemList.any()))
                .leftJoin(QUser.user).on(QUser.user.eq(QOrder.order.user))
                .where(orderStatusEq(searchOrder.getOmode()),
                        buyerEq(searchOrder.getSinput()),
                        betweenDate(searchOrder.getFirstdate(), searchOrder.getLastdate())
                )
                .orderBy(QOrderItem.orderItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainPageOrderDto> mainPageSearchAllOrder(Pageable pageable, String loginId) {
        QueryResults<MainPageOrderDto> results = queryFactory
                .select(new QMainPageOrderDto(
                        QOrder.order.id,
                        QOrderItem.orderItem.id,
                        QOrderItem.orderItem.item.id,
                        QUser.user.name,
                        QOrderItem.orderItem.item.itemName,
                        QOrder.order.orderedAt,
                        QOrderItem.orderItem.orderStatus,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.count,
                        QOrderItem.orderItem.item.imgUrl,
                        QOrderItem.orderItem.item.color
                ))
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem).on(QOrderItem.orderItem.eq(QOrder.order.orderItemList.any()))
                .leftJoin(QUser.user).on(QUser.user.eq(QOrder.order.user))
                .where(QUser.user.loginId.eq(loginId), QOrderItem.orderItem.item.rep.eq(true))
                .orderBy(QOrderItem.orderItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainPageOrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainPageOrderDto> mainPageSearchAllOrderByCondition(SearchOrder searchOrder, Pageable pageable, String loginId) {
        QueryResults<MainPageOrderDto> results = queryFactory
                .select(new QMainPageOrderDto(
                        QOrder.order.id,
                        QOrderItem.orderItem.id,
                        QOrderItem.orderItem.item.id,
                        QUser.user.name,
                        QOrderItem.orderItem.item.itemName,
                        QOrder.order.orderedAt,
                        QOrderItem.orderItem.orderStatus,
                        QOrderItem.orderItem.orderPrice,
                        QOrderItem.orderItem.count,
                        QOrderItem.orderItem.item.imgUrl,
                        QOrderItem.orderItem.item.color
                ))
                .from(QOrder.order)
                .leftJoin(QOrderItem.orderItem).on(QOrderItem.orderItem.eq(QOrder.order.orderItemList.any()))
                .leftJoin(QUser.user).on(QUser.user.eq(QOrder.order.user))
                .where(QUser.user.loginId.eq(loginId),
                        QOrderItem.orderItem.item.rep.eq(true),
                        orderStatusEq(searchOrder.getOmode()),
                        betweenDate(searchOrder.getFirstdate(), searchOrder.getLastdate())
                )
                .orderBy(QOrderItem.orderItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainPageOrderDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression betweenDate(String firstDate, String lastDate) {
        if (StringUtils.isEmpty(firstDate) && StringUtils.isEmpty(lastDate)) {

            LocalDate start = LocalDate.now().minusYears(10L);
            LocalDate end = LocalDate.now();

            return QOrder.order.orderedAt.between(start, end);
        } else if (StringUtils.isNotEmpty(firstDate) && StringUtils.isEmpty(lastDate)) {
            LocalDate start = LocalDate.parse(firstDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.now();

            return QOrder.order.orderedAt.between(start, end);
        } else if (StringUtils.isEmpty(firstDate) && StringUtils.isNotEmpty(lastDate)) {

            LocalDate start = LocalDate.now().minusYears(10L);
            LocalDate end = LocalDate.parse(lastDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//           LocalDate 와 LocalDateTime의 차이로 인하여 min을 하면 형식이 이것이 아니라 변하게 된다. 그래서 임의로 10년 전으로 계산한다.

            return QOrder.order.orderedAt.between(start, end);
        } else {
            LocalDate start = LocalDate.parse(firstDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = LocalDate.parse(lastDate);

            return QOrder.order.orderedAt.between(start, end);
        }
    }

    private BooleanExpression buyerEq(String buyerCondition) {
        if (StringUtils.isEmpty(buyerCondition)) {
            return null;
        }
        return QUser.user.name.likeIgnoreCase("%" + buyerCondition + "%");
    }

    private BooleanExpression orderStatusEq(String orderStatusCondition) {
        return QOrderItem.orderItem.orderStatus.stringValue().eq(orderStatusCondition);
    }
}
