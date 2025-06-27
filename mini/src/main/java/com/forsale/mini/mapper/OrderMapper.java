package com.forsale.mini.mapper;
import com.forsale.mini.bean.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    // Insert a new order
    @Insert("INSERT INTO `order` (user_id, order_date, totalprice, status) " +
            "VALUES (#{userId}, #{orderDate}, #{totalPrice}, #{status})")
    void insertOrder(Order order);

    // Get all orders
    @Select("SELECT * FROM `order`")
    List<Order> getAllOrders();

    // Get an order by ID
    @Select("SELECT * FROM `order` WHERE order_id = #{orderId}")
    Order getOrderById(Integer orderId);

    // Update an order by ID
    @Update("UPDATE `order` SET user_id = #{userId}, order_date = #{orderDate}, " +
            "totalprice = #{totalPrice}, status = #{status} WHERE order_id = #{orderId}")
    void updateOrder(Order order);

    // Delete an order by ID
    @Delete("DELETE FROM `order` WHERE order_id = #{orderId}")
    void deleteOrder(Integer orderId);
}
