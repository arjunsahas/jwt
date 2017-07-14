package org.arjun.jwt.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by arjuns on 4/7/17.
 */
@WebFilter(displayName = "login", filterName = "login", urlPatterns = "/login", description = "Auth Filter")
public class AuthFilter implements Filter {

    public static final String TOKEN = "TOKEN";
    private final AuthService authService = new AuthService();

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            final ServletRequest request, final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ((StringUtils.isBlank(username) || StringUtils.isBlank(password)) && StringUtils.isBlank(req.getHeader
                ("Authorization")) && checkTokenCookie(req.getCookies()) == null) {
            resp.sendRedirect("http://localhost:9080/JWT");
            return;
        }

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            String token = req.getHeader("Authorization");
            if (StringUtils.isNotBlank(token)) {
                boolean success = authService.verify(token, req);
                successOrFailure(resp, success);
            } else {
                Cookie tokenCookie = checkTokenCookie(req.getCookies());
                if (tokenCookie != null) {
                    String value = tokenCookie.getValue();
                    boolean verify = authService.verify(value, req);
                    successOrFailure(resp, verify);
                } else {
                    sendAuthenticationFailure(resp);
                }
            }
        } else {
            String jwtToken = authService.authenticate(username, password, req);
            if (StringUtils.isNotBlank(jwtToken)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                Cookie tokenCookie = new Cookie(TOKEN, jwtToken);
                tokenCookie.setDomain(req.getServerName());
                tokenCookie.setPath(req.getContextPath().substring(1));
                tokenCookie.setHttpOnly(true);
                tokenCookie.setMaxAge(60 * 10);
                resp.addCookie(tokenCookie);

                URL resource = getClass().getResource("/jwttokenpost.html");
                byte[] bytes = IOUtils.toByteArray(resource);
                String output = new String(bytes);
                output = output.replace("${token}", jwtToken);
                PrintWriter writer = resp.getWriter();
                writer.append(output);
                resp.flushBuffer();
            } else {
                sendAuthenticationFailure(resp);
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void successOrFailure(final HttpServletResponse resp, final boolean success) throws IOException {
        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
            PrintWriter writer = resp.getWriter();
            writer.append("authenticated");
        } else {
            sendAuthenticationFailure(resp);
        }
    }

    private void sendAuthenticationFailure(final HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.flushBuffer();
    }

    private Cookie checkTokenCookie(Cookie[] cookies) {
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TOKEN)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
