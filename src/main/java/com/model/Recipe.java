package com.model;

public class Recipe {
    private String name;
    private String imgPath;
    private String ingredients;
    private String howToCook;
    private int uId;

    public Recipe(int uId, String name, String imgPath, String ingredients, String howToCook) {
        this.uId = uId;
        this.name = name;
        this.imgPath = imgPath;
        this.ingredients = ingredients;
        this.howToCook = howToCook;
    }

    public Recipe(int uId, String name, String imgPath) {
        this.uId = uId;
        this.name = name;
        this.imgPath = imgPath;
        this.ingredients = "";
        this.howToCook = "";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getHowToCook() {
        return howToCook;
    }

    public void setHowToCook(String howToCook) {
        this.howToCook = howToCook;
    }

    public int getId() {
        return uId;
    }

    public void setId(int uId) {
        this.uId = uId;
    }
}
