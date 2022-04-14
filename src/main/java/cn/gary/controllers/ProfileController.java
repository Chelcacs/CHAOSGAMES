package cn.gary.controllers;

import cn.gary.models.TUserUser;
import cn.gary.service.TUserUserService;
import com.aliyun.oss.OSSClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.net.HttpCookie;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProfileController {

    @Autowired
    TUserUserService service;

    @GetMapping("profilepage")
    public String profile(HttpSession session, Model model) {
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("user_info", user_info);
            model.addAttribute("useravatar",user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
            return "profilepage";
        }else{
            model.addAttribute("logstate",0);
            return "redirect:/login?backto=%2Fprofilepage";
        }

    }

    @PostMapping("profilepage")
    public String profilepage(String new_avatar, String user_name) {
        TUserUser user_info = service.findbyusername(user_name);
        user_info.setUser_avatar(new_avatar);
        service.update(user_info);
        return null;
    }

    @GetMapping("profiles/accountinfo")
    public String accountinfo(HttpSession session, Model model){
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("user_info", user_info);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String birthday = sdf.format(info.getUser_birthday());
//            model.addAttribute("userbirthday", birthday);
            return "profile";
        }else{
            model.addAttribute("logstate",0);
            return "redirect:/login";
        }
    }


}
