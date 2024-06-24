package com.repository;

import com.model.Recipe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RecipesRepository {
    // Returns a List of recipes by page (Using OFFSET option if SQL-query)
    public static ArrayList<Recipe> getRecipeListByPage(Integer pageCounter) throws SQLException, ClassNotFoundException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Connection con = Connector.connect_to_recipes();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Recipes DESC LIMIT 5 OFFSET " + pageCounter.toString());
        while (rs.next()) {
            int id = rs.getInt("Id");
            Recipe recipe = new Recipe(
                    id,
                    rs.getString("Name"),
                    Integer.toString(id) + ".jpg",
                    rs.getString("Ingredients"),
                    rs.getString("HowTo"));
            recipes.add(recipe);
        }
        rs.close();
        stmt.close();
        con.close();
        return recipes;
    }
}
