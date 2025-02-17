package com.denbondd.restaurant.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String getErrMessage(Exception e) {
        return e + "; Caused by: " + e.getCause().toString();
    }

    // destroying the life cycle of session
    public static void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();  // destroy the life cycle of session
        resp.sendRedirect(req.getContextPath() + "/catalog");
    }
}
