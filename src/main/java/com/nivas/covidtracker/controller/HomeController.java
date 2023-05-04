package com.nivas.covidtracker.controller;

import com.nivas.covidtracker.model.LocationStats;
import com.nivas.covidtracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService cvds;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> all=cvds.getAllList();
        int total=all.stream().mapToInt(stat->stat.getLatest()).sum();
        int totalreportedcases=all.stream().mapToInt(stat->stat.getDiff()).sum();
        model.addAttribute("cvds",cvds.getAllList());
        model.addAttribute("total",total);
        model.addAttribute("trc",totalreportedcases);
        return "home";
    }
}
