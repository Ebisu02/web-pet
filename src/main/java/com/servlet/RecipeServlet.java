package com.servlet;

import com.repository.Connector;
import com.google.gson.Gson;
import com.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/recipes")
public class RecipeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() +*/ "/jsp/recipes/recipes.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Integer pageCounter;
        try {
            pageCounter = Integer.valueOf(req.getParameter("page")) - 1;
            if (pageCounter < 0) {
                pageCounter = 0;
            }
        } catch (Exception e) {
            pageCounter = 0;
        }
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
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
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        Gson gson = new Gson();
        String json = gson.toJson(recipes);
        out.println(json);
    }
}
