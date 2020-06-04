package service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;

public class MyCookie {

    public static  void add(String name, String value, HttpServletResponse response){
        Cookie c = new Cookie(name, value);
        c.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(c);
    }

    public static String get(String name, HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        HashMap<String, String> cookieMap = new HashMap<>();
      //  Arrays.stream(cookies).map(e->cookieMap.put(e.getName(),e.getValue()));
        Arrays.stream(cookies).forEach(e->cookieMap.put(e.getName(),e.getValue()));
        return cookieMap.get(name);
    }

    public static void remove(HttpServletRequest req, HttpServletResponse resp){
        Cookie[] cookies = req.getCookies();
        Arrays.stream(cookies)
                .forEach(c -> {
                    c.setMaxAge(0);
                    resp.addCookie(c);
                });
    }
}
