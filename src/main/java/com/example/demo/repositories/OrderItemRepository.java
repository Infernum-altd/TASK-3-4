package com.example.demo.repositories;

import com.example.demo.models.OrderItem;
import com.example.demo.utils.DbUtils;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
public class OrderItemRepository {
    private static final String saveQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private static final String getOrderItemsByOrderId = "SELECT order_id, product_id, quantity FROM order_items WHERE order_id = ?";

    public static void save(OrderItem orderItem) {
        try (Connection connection = DbUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(saveQuery);

            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public static List<OrderItem> getOrderItemsByOrderId(int id) {
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection connection = DbUtils.getConnection()){
            PreparedStatement statement = connection.prepareStatement(getOrderItemsByOrderId);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(id);
                orderItem.setProductId(resultSet.getInt("product_id"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                orderItems.add(orderItem);
            }
            resultSet.close();
        }catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return orderItems;
    }
}
