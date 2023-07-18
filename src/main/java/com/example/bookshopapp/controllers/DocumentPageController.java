package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.other.DocumentEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/documents")
@Api(description = "documents data")
public class DocumentPageController {

    @GetMapping("")
    public String documentsPage() {
        return "documents/index";
    }

    @GetMapping("/{id}")
    public String documentById(@PathVariable(value = "id") int id, Model model) {
        return "documents/slug";
    }

    @GetMapping("/api")
    @ResponseBody
    @ApiOperation("method to get list of documents")
    public List<DocumentEntity> documentsApi() {
        //TODO
        return new ArrayList<>();
    }
}
