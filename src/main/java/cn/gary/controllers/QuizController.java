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
public class QuizController {

    @Autowired
    TUserUserService service;

    @GetMapping("quizpage")
    public String quiz(HttpSession session, Model model, HttpServletResponse response) throws IOException {
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("useravatar",user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
            return "quizpage";
        }else{
            model.addAttribute("logstate",0);
            response.setContentType("text/html;charset=utf-8");
            String scriptinfo = "<script>alert(\"Please login first!\");window.location.href='/login';</script>";
            //将数据直接发送到客户端浏览器
            response.getWriter().print(scriptinfo);
            return null;
        }
    }
}
