package com.example.demo.views;


import com.example.demo.models.Order;
import com.example.demo.utils.ConsoleReadWrite;
import com.example.demo.utils.PropertyReader;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RepresentOrdersView extends View {
    private final List<Order> orders;

    public RepresentOrdersView(List<Order> orders) {
        super(PropertyReader.getProperty("represent.orders.view"));
        this.orders = orders;

        StringBuilder stringBuilder = new StringBuilder();
        for (Order order : orders) {
            stringBuilder.append("         " + order.getId() + "              " + order.calculateTotalPrice() +
                    "                  " + order.calculateTotalItemsQuantity() + "          " +
                    order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n");
        }
        super.navigationMessage += "\n" + stringBuilder.toString();
    }

    @Override
    public View interact(String input) {
        return new HomeView();
    }

    @Override
    public View interact() {
        ConsoleReadWrite.clearConsole();
        showNavigationMessage();

        for (Order order : orders) {
            showNavigationMessage("         " + order.getId() + "              " + order.calculateTotalPrice() +
                    "                  " + order.calculateTotalItemsQuantity() + "          " +
                    order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        showNavigationMessage(PropertyReader.getProperty("enter.something"));
        ConsoleReadWrite.readConsoleInput();
        return new HomeView();
    }
}
