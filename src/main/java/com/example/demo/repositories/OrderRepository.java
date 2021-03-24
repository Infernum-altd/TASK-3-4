package com.example.demo.repositories;

import com.example.demo.models.Order;
import com.example.demo.models.OrderStatus;
import com.example.demo.utils.DbUtils;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
public class OrderRepository {
    private static final String saveQuery = "INSERT INTO orders (user_id, status, created_at) VALUES (?, ?, ?)";
    private static final String selectAll = "SELECT id, user_id, status, created_at FROM orders";
    private static final String selectOrderById = "SELECT id, user_id, status, created_at FROM orders WHERE id = ?";

    public static void save(Order order) {
        try (Connection connection = DbUtils.getConnection()){
            PreparedStatement statement = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, order.getUserId());
            statement.setString(2, order.getStatus().toString());
            statement.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no row affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);

                    order.getOrderItems().forEach(orderItem -> orderItem.setOrderId(orderId));
                    order.getOrderItems().forEach(OrderItemRepository::save);
                }
                else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        }catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public static List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DbUtils.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAll);

            mapResultSetToOrders(orderList, resultSet);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return orderList;
    }

    public static List<Order> getOrderById(int orderIr) {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DbUtils.getConnection()){
            PreparedStatement statement = connection.prepareStatement(selectOrderById);
            statement.setInt(1, orderIr);
            ResultSet resultSet = statement.executeQuery();

            mapResultSetToOrders(orderList, resultSet);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return orderList;
    }

    private static void mapResultSetToOrders(List<Order> orderList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt("id"));
            order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
            order.setUserId(resultSet.getInt("user_id"));
            order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            order.setOrderItems(OrderItemRepository.getOrderItemsByOrderId(order.getId()));
            orderList.add(order);
        }
        resultSet.close();
    }
}
