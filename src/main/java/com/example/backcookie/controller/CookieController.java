package com.example.backcookie.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cookie")
@Slf4j
public class CookieController {
    @GetMapping("/set")
    public String setCookie(HttpServletResponse response) {
        // create a cookie
        Cookie cookie = new Cookie("user", "john-doe");
        // Thời gian sống của cookie (đơn vị: giây)
        cookie.setMaxAge(900);
        // Chỉ cho phép truy cập qua HTTP, không cho phép qua JavaScript
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "Cookie đã được tạo và gửi.";
    }

    @GetMapping("/get")
    public String getCookie(@CookieValue(value = "color",
        defaultValue = "No color found in cookie") String color) {
        return "Sky is: " + color;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/readCookie")
    public void readCookie(@CookieValue(value = "fav-col",
        defaultValue = "unknown") String favColour) {
        log.info("Favourite colour: {}", favColour);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/writeCookie")
    public ResponseEntity writeCookie() {

        var favColour = "steelblue";
        var cookie = ResponseCookie.from("fav-col", favColour).maxAge(900).httpOnly(true).build();

        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .build();
    }
}
