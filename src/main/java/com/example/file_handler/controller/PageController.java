package com.example.file_handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/file-handler")
    public String fileHandlerPage() {
        return "file-handler"; // 對應 file-handler.jsp
    }
}

