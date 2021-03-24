package com.example.demo.services;

import com.example.demo.views.View;

public interface UserView {
    View getUserViewByUserId(String id);
    void addUserView(String id);
    void interact(String answer, String userId);
}
