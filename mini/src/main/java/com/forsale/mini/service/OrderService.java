package com.forsale.mini.service;
import com.forsale.mini.bean.Order;
import com.forsale.mini.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public void addOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    public Order getOrderById(Integer orderId) {
        return orderMapper.getOrderById(orderId);
    }

    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    public void deleteOrder(Integer orderId) {
        orderMapper.deleteOrder(orderId);
    }
}
