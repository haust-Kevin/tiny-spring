package org.example.controller;

import org.example.beans.Autowired;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.web.mvc.Controller;
import org.example.web.mvc.RequestMapping;
import org.example.web.mvc.RequestParam;

@Controller
public class HelloController {

    @Autowired
    private UserService userService;


    @RequestMapping("/hello")
    public String hello() {
        return "<h1>Hello</h1>";
    }


    @RequestMapping("/hello2")
    public String hello2(@RequestParam("age") String 年龄, String name) {
        return String.format("<h1>Hello World</h1>\n<h2>age=%s, name=%s</h2>", 年龄, name);
    }

    @RequestMapping("/user")
    public String user() {
        User user = userService.getUser();
        return String.format("{\"name\":\"%s\", \"age\":%d}", user.getName(), user.getAge());
    }

}
