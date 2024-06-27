package com.oa.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatementController {
    @GetMapping("/profitloss")
    public String statementForm() {
        return "statement"; //note that this says .html
    }

    @GetMapping("/profitloss/result")
    public String resultStatement() {
        return "statementResponse"; //note that this says .html
    }

    @GetMapping("/kellyCalculator")
    public String kellyCalculator() {
        return "kellyCalculator";
    }

    @GetMapping("/home")
    public String index() {
        return "index";
    }
}
