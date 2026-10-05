package com.smartresume.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {
        "/dashboard.html",
        "/editor.html",
        "/preview.html",
        "/profile.html"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            javax.servlet.ServletRequest request,
            javax.servlet.ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        boolean loggedIn =
                session != null
                        && session.getAttribute("userId") != null;

        if (loggedIn) {

            chain.doFilter(request, response);

        } else {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/login.html?error=login_required"
            );
        }
    }
}