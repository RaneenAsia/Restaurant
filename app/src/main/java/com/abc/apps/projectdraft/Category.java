package com.abc.apps.projectdraft;

import java.io.Serializable;

public class Category {
    private String name;
    private int image;

    private Category(String name, int image ){
        this.name = name;
        this.image = image;
    }

    public static final Category[] categories = {
            new Category("Burgers", R.drawable.burgers),
            new Category("Pizzas", R.drawable.pizza),
            new Category("Sandwiches", R.drawable.sandwich),
            new Category("Pasta",R.drawable.pasta), new Category("HotDogs",R.drawable.hotdog),
            new Category("Appetizers",R.drawable.appetizer),
            new Category("Drinks", R.drawable.drinks),
            new Category("Soups", R.drawable.soup),
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
