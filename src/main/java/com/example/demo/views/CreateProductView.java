package com.example.demo.views;


import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.utils.ConsoleReadWrite;
import com.example.demo.utils.PropertyReader;

public class CreateProductView extends View {
    public CreateProductView() {
        super(PropertyReader.getProperty("create.product.message"));
    }

    public CreateProductView(String extraMessage) {
        super(PropertyReader.getProperty("create.product.message") + "\n" + extraMessage);
    }

    @Override
    public View interact(String input) {
        View nextView;

        if (input.matches("\\w+ \\d+ [123]")) {
            Product createdProduct = Product.createProductFromInput(input);
            ProductRepository.save(createdProduct);
            nextView = new CreateProductView(PropertyReader.getProperty("create.product.completed"));
        } else {
            switch (input) {
                case "back":
                    nextView = new CreateView();
                    break;
                case "home":
                    nextView = new HomeView();
                    break;
                default:
                    nextView = new CreateProductView(PropertyReader.getProperty("wrong.input.simple"));
            }
        }
        return nextView;
    }

    @Override
    public View interact() {
        ConsoleReadWrite.clearConsole();
        View nextView;
        showNavigationMessage();
        String input = ConsoleReadWrite.readConsoleInput();

        if (input.matches("\\w+ \\d+ [123]")) {
            Product createdProduct = Product.createProductFromInput(input);
            ProductRepository.save(createdProduct);
            ConsoleReadWrite.showSuccessfulMessageWithTimePause(PropertyReader.getProperty("create.product.completed"));
            nextView = this;
        } else {
            switch (input) {
                case "back":
                    nextView = new CreateView();
                    break;
                case "home":
                    nextView = new HomeView();
                    break;
                default:
                    ConsoleReadWrite.showErrorMessageWithTimePause(PropertyReader.getProperty("wrong.input.simple"));
                    nextView = this;
            }
        }
        return nextView;
    }
}
