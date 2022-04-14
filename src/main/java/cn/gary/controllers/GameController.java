package cn.gary.controllers;


import cn.gary.models.TUserUser;
import cn.gary.service.TUserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class GameController {

    @Autowired
    TUserUserService service;

    @GetMapping("gamespage")
    public String gamespage(HttpSession session, Model model){
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("useravatar",user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
            return "gamespage";
        }else{
            model.addAttribute("logstate",0);
            return "gamespage";
        }
    }

    @GetMapping("games/chuckchicken")
    public String chuckchicken(){
        return "games/chuckchicken";
    }

    @GetMapping("games/cutemonster")
    public String cutemonster(){
        return "games/cutemonster";
    }

    @GetMapping("games/memory")
    public String memory(HttpSession session, Model model, HttpServletResponse response) throws IOException {
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            String username = (String)session.getAttribute("username");
            model.addAttribute("username",username);
            return "games/memory";
        }else{
            return "redirect:/login?backto=%2Fgames%2Fmemory";
        }
    }



}
