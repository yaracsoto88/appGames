package com.example.a2048;

import com.example.a2048.game2048.game2048;
import com.example.a2048.senku.Senku;

import java.util.ArrayList;

public class MenuItem {
    private String title;
    private final int imageResource;
    private Class activity;

    MenuItem(String title, int imageResource, Class activity) {
        this.title = title;
        this.imageResource = imageResource;
        this.activity = activity;
    }

    String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public Class getActivity() {
        return activity;
    }

    public static ArrayList<MenuItem> getMenu() {
        ArrayList<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem("2048", R.drawable.game2048, game2048.class));
        menu.add(new MenuItem("Senku", R.drawable.senku, Senku.class));
        menu.add(new MenuItem("Score", R.drawable.score, ScoreActivity.class));
        menu.add(new MenuItem("Settings", R.drawable.settings, Setting.class));
        return menu;
    }
}
