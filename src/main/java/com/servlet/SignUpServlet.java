package com.servlet;

import com.exception.NonUniqueEmailException;
import com.exception.NonUniqueNameException;
import com.model.User;
import com.repository.UserRepository;
import com.service.PostService;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = /*req.getContextPath() +*/ "/jsp/signup/signup.jsp";
        getServletContext().getRequestDispatcher(path).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String registrationHeader = req.getHeader("Registration");
        HttpSession session = req.getSession();
        resp.addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = resp.getWriter();
        if (registrationHeader.equals(null)) {
            throw new ServletException("RegistrationHeader is null");
        }
        StringTokenizer stringTokenizer = new StringTokenizer(registrationHeader);
        try {
            User user = UserService.register(UserRepository.getNextIdFromTableUsers(), stringTokenizer);
            if (!UserRepository.checkIsNameUnique(user.getName())) {
                throw new NonUniqueNameException();
            }
            if (!UserRepository.checkIsEmailUnique(user.getEmail())) {
                throw new NonUniqueEmailException();
            }
            UserRepository.insertUserIntoDatabase(user);
            PostService.postStatus(out, PostService.STATUS_SUCCESS);
        } catch (NonUniqueNameException e) {
            PostService.postExceptionStatus(out, "nonUniqueName", e);
        } catch (NonUniqueEmailException e) {
            PostService.postExceptionStatus(out, "nonUniqueEmail", e);
        } catch(Exception e) {
            PostService.postExceptionStatus(out, PostService.STATUS_FAILED, e);
        }
    }
}
