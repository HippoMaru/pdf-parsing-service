package com.hippomaru.pdfParsingService.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/fileDetails/{id}")
    public String showMainPage(@PathVariable Integer id, Model model) {
        model.addAttribute("documentId", id);
        return "file-details";
    }
}
