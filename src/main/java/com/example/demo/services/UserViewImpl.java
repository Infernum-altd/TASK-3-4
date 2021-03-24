package com.example.demo.services;

import com.example.demo.views.HomeView;
import com.example.demo.views.View;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserViewImpl implements UserView{
    private Map<String, View> stringViewMap = new ConcurrentHashMap<>();

    @Override
    public View getUserViewByUserId(String id) {
        return stringViewMap.get(id);
    }

    @Override
    public void addUserView(String id) {
        stringViewMap.put(id, new HomeView());
    }

    @Override
    public void interact(String answer, String userId) {
        View newView = stringViewMap.get(userId).interact(answer);
        stringViewMap.put(userId, newView);
    }
}
