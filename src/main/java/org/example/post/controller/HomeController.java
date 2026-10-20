package org.example.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * 메인 화면
     * [GET] /
     */
    @GetMapping({"", "/"})
    public String home() {
        return "index";
    }

}
