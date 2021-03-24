package com.example.demo.views;


import com.example.demo.utils.ConsoleReadWrite;
import com.example.demo.utils.PropertyReader;

public class HomeView extends View {
    public HomeView() {
        super(PropertyReader.getProperty("home.view.message"));
    }

    public HomeView(String extraMessage) {
        super(PropertyReader.getProperty("home.view.message") + "\n" + extraMessage);
    }

    @Override
    public View interact() {
        ConsoleReadWrite.clearConsole();
        View nextView;
        showNavigationMessage();
        String input = ConsoleReadWrite.readConsoleInput();

        switch (input) {
            case "1":
                nextView = new CreateView();
                break;
            case "2":
                nextView = new RemoveView();
                break;
            case "3":
                nextView = new RepresentView();
                break;
            case "4":
                System.exit(0);
            default:
                ConsoleReadWrite.showErrorMessageWithTimePause(PropertyReader.getProperty("wrong.input.simple"));
                nextView = this;
        }

        return nextView;
    }

    @Override
    public View interact(String input) {
        View nextView;

        switch (input) {
            case "1":
                nextView = new CreateView();
                break;
            case "2":
                nextView = new RemoveView();
                break;
            case "3":
                nextView = new RepresentView();
                break;
            case "4":
                System.exit(0);
            default:
                nextView = new HomeView(PropertyReader.getProperty("wrong.input.simple"));
        }

        return nextView;
    }
}
