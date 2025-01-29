package com.presnakov.hotelbooking.service;

import com.presnakov.hotelbooking.database.entity.Order;
import com.presnakov.hotelbooking.database.entity.OrderStatusEnum;
import com.presnakov.hotelbooking.database.repository.OrderRepository;
import com.presnakov.hotelbooking.dto.OrderCreateEditDto;
import com.presnakov.hotelbooking.dto.OrderFilter;
import com.presnakov.hotelbooking.dto.OrderReadDto;
import com.presnakov.hotelbooking.mapper.OrderCreateEditMapper;
import com.presnakov.hotelbooking.mapper.OrderReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;

    @Transactional
    public Page<OrderReadDto> findAll(OrderFilter filter, Pageable pageable) {
        Page<Order> ordersPage = orderRepository.findAll(filter, pageable);

        ordersPage.getContent().stream()
                .filter(order -> order.getCheckOutDate().isBefore(LocalDate.now()))
                .forEach(order -> {
                    order.setStatus(OrderStatusEnum.CLOSED);
                    orderRepository.save(order);
                });

        return ordersPage.map(orderReadMapper::map);
    }

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderCreateEditDto) {
        return Optional.of(orderCreateEditDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Integer id, OrderCreateEditDto orderCreateEditDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderCreateEditDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.softDelete(order);
                    order.setStatus(OrderStatusEnum.CLOSED);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
