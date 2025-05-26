package com.hippomaru.pdfParsingService.config;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front")
public class ViewController {

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload-form";
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "main";
    }
}
