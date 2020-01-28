package com.haoming.controllers;

import com.haoming.web.mvc.Controller;
import com.haoming.web.mvc.RequestMapping;
import com.haoming.web.mvc.RequestParam;

/**
 * Controller替代了原本的Servlet接收request
 *
 * @author 58212
 * @date 2020-01-28 21:23
 */
@Controller
public class SalaryController {
    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return 10000;
    }
}
