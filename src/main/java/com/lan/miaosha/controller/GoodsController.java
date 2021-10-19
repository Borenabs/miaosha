package com.lan.miaosha.controller;

import com.lan.miaosha.domain.MiaoShaUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoodsController {

    @RequestMapping("/to_goodsList")
    public String goodsPage(Model model){
        model.addAttribute("user" , new MiaoShaUser());
        return "goods_list";
    }
}
