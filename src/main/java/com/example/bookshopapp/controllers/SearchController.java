package com.example.bookshopapp.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
@Api
public class SearchController {

    @GetMapping("/api")
    @ApiOperation("")
    public String searchApi(){
        return "";
    }
}
