package com.servlet;

import com.model.Recipe;
import com.repository.RecipesRepository;
import com.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/recipes")
public class RecipeServlet extends HttpServlet {

    ArrayList<Recipe> recipes;

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
        Integer pageCounter = getPageCounter(req);
        try {
            recipes = RecipesRepository.getRecipeListByPage(pageCounter);
        } catch (Exception e) {
            PostService.postExceptionStatus(out, "failed", e);
        }
        PostService.postRecipeList(out, recipes);
    }

    private Integer getPageCounter(HttpServletRequest req) {
        try {
            Integer pageCounter = Integer.valueOf(req.getParameter("page")) - 1;
            if (pageCounter < 0) {
                return 0;
            }
            return pageCounter;
        } catch (Exception e) {
            return 0;
        }
    }
}
