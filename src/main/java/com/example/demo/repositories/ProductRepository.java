package com.example.demo.repositories;

import com.example.demo.models.Product;
import com.example.demo.models.ProductStatus;
import com.example.demo.utils.DbUtils;
import lombok.extern.java.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
public class ProductRepository {
    private static final String saveQuery = "INSERT INTO products (name, price, status, created_at) values (?, ?, ?::status, ?)";
    private static final String selectProductById = "SELECT id, name, price, status, created_at FROM products WHERE id = ?";
    private static final String deleteProductBtId = "DELETE FROM products WHERE id = ?";
    private static final String deleteAll = "DELETE FROM products";
    private static final String selectAllProducts = "SELECT name, price, status, created_at FROM products";
    private static final String selectOrderedProduct = "SELECT products.id, products.name, products.price, products.status, SUM(order_items.quantity) as total \n" +
            "FROM products INNER JOIN order_items ON order_items.product_id = products.id\n" +
            "            GROUP BY order_items.product_id, products.id\n" +
            "         ORDER BY total DESC";

    public static void save(Product product) {
        try (Connection connection = DbUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(saveQuery);

            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getStatus().toString());
            statement.setTimestamp(4, Timestamp.valueOf(product.getCreatedAt()));

            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public static boolean isProductExist(int productId) {
        boolean result = false;
        try (Connection connection = DbUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectProductById);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();

            resultSet.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }

    public static void deleteById(int id) {
        try (Connection connection = DbUtils.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteProductBtId);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void deleteAll() {
        try (Connection connection = DbUtils.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteAll);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DbUtils.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllProducts);

            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setStatus(ProductStatus.valueOf(resultSet.getString("status").toUpperCase()));
                product.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                products.add(product);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return products;
    }

    public static Product getProductById(int productId) {
        Product product = new Product();
        try (Connection connection = DbUtils.getConnection()){
            PreparedStatement statement = connection.prepareStatement(selectProductById);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            product.setName(resultSet.getString("name"));
            product.setStatus(ProductStatus.valueOf(resultSet.getString("status").toUpperCase()));
            product.setPrice(resultSet.getInt("price"));
            product.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());

            resultSet.close();
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return product;
    }

    public static Object[][] getOrderedProductsWithTotalQuantity() {
        Object[][] arrProductTotalOrder = null;
        try (Connection connection = DbUtils.getConnection()){
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(selectOrderedProduct);

            resultSet.last();
            int numberOfRows = resultSet.getRow();
            resultSet.first();
            resultSet.previous();
            arrProductTotalOrder = new Object[numberOfRows][2];
            int counter = 0;
            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setStatus(ProductStatus.valueOf(resultSet.getString("status").toUpperCase()));
                product.setPrice(resultSet.getInt("price"));

                arrProductTotalOrder[counter][0] = product;
                arrProductTotalOrder[counter][1] = resultSet.getInt("total");
                counter++;
            }

            resultSet.close();
        }catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return arrProductTotalOrder;
    }
}
