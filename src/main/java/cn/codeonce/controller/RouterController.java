package cn.codeonce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name: RouterController
 * @Description: TODO
 * @Author: codeonce
 * @Date: 2022/5/26
 */

@Controller
public class RouterController {

    @RequestMapping({"/", "/index", "/index.html"})
    public String index() {
        return "index";
    }


    @RequestMapping("/toLogin")
    public String toLogin() {
        return "views/login";
    }


    @RequestMapping("/level{level}/{id}")
    public String level1(@PathVariable("level") Integer level, @PathVariable("id") Integer id) {
        return "views/level" + level + "/" + id;
    }

}
