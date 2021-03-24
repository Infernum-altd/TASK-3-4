package com.example.demo.views;


import com.example.demo.utils.ConsoleReadWrite;
import com.example.demo.utils.PropertyReader;

public class CreateView extends View{
    public CreateView() {
        super(PropertyReader.getProperty("create.view.message"));
    }

    public CreateView(String extraMessage) {
        super(PropertyReader.getProperty("create.view.message") + "\n" + extraMessage);
    }

    @Override
    public View interact(String input) {
        View nextView;
        switch (input) {
            case "1":
                nextView = new CreateProductView();
                break;
            case "2":
                nextView = new CreateOrderView();
                break;
            case "3":
                nextView = new HomeView();
                break;
            case "4":
                System.exit(0);
            default:
                nextView = new CreateView(PropertyReader.getProperty("wrong.input.simple"));
        }
        return nextView;
    }

    @Override
    public View interact() {
        ConsoleReadWrite.clearConsole();
        View nextView;
        showNavigationMessage();
        String input = ConsoleReadWrite.readConsoleInput();

        switch (input) {
            case "1":
                nextView = new CreateProductView();
                break;
            case "2":
                nextView = new CreateOrderView();
                break;
            case "3":
                nextView = new HomeView();
                break;
            case "4":
                System.exit(0);
            default:
                ConsoleReadWrite.showErrorMessageWithTimePause(PropertyReader.getProperty("wrong.input.simple"));
                nextView = this;
        }
        return nextView;
    }
}
