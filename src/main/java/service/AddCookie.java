package service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class AddCookie {

    public static  void add(String name, String value, HttpServletResponse response){
        Cookie c = new Cookie(name, value);
        c.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(c);
    }
}
