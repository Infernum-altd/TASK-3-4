package com.example.demo.views;

import lombok.Data;

@Data
public abstract class View {
    protected String navigationMessage;
    protected String extraMessage;
    public abstract View interact();
    public abstract View interact(String input);

    public View(String navigationMessage) {
        this.navigationMessage = navigationMessage;
    }

    public View(String navigationMessage, String extraMessage) {
        this.navigationMessage = navigationMessage;
        this.extraMessage = extraMessage;
    }

    protected void showNavigationMessage() {
        System.out.println(navigationMessage);
    }

    protected void showNavigationMessage(String navigationMessage) {
        System.out.println(navigationMessage);
    }
}
