package com.example.demo.views;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.utils.ConsoleReadWrite;
import com.example.demo.utils.PropertyReader;

public class RepresentOrderedProductsView extends View {
    public RepresentOrderedProductsView() {
        super(PropertyReader.getProperty("represent.ordered.products.view"));

        Object[][] arrProductTotalOrder = ProductRepository.getOrderedProductsWithTotalQuantity();
        StringBuilder stringBuilder = new StringBuilder();
        if (arrProductTotalOrder != null) {
            for (Object[] objects : arrProductTotalOrder) {
                Product product = (Product) objects[0];
                stringBuilder.append("     " + product.getName() + "           " +
                        product.getPrice() + "              " +
                        product.getStatus() + "      " + objects[1] + "\n");
            }
        }
        super.navigationMessage += ("\n" + stringBuilder.toString());
    }

    @Override
    public View interact(String input) {
        return new HomeView();
    }

    @Override
    public View interact() {
        ConsoleReadWrite.clearConsole();
        showNavigationMessage();
        Object[][] arrProductTotalOrder = ProductRepository.getOrderedProductsWithTotalQuantity();

        for (Object[] objects : arrProductTotalOrder) {
            Product product = (Product) objects[0];
            showNavigationMessage("     " + product.getName() + "           " +
                    product.getPrice() + "              " +
                    product.getStatus() + "      " + objects[1]);
        }

        showNavigationMessage(PropertyReader.getProperty("enter.something"));
        ConsoleReadWrite.readConsoleInput();
        return new HomeView();
    }
}
