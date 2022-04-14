package cn.gary.controllers;

import cn.gary.pojo.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import cn.gary.models.TUserUser;
import cn.gary.service.TUserUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 * 前台
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    TUserUserService service;
    @Autowired
    MailService sendemail;
    //@RequestMapping("/index")
    //@RequestMapping(value = "/index", method = RequestMethod.GET)
    @GetMapping("index")
    //@ResponseBody
    public String index(HttpSession session, Model model){
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("useravatar",user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
        }else{
            model.addAttribute("logstate",0);
        }
        return "index";
    }

    @PostMapping("index")
    public String index(String gamechio, Model model) {
        if (gamechio.equals("lol") || gamechio.equals("LOL")) {
            return "redirect:lol";
        } else if (gamechio.equals("csgo") || gamechio.equals("CSGO")) {
            return "redirect:csgo";
        } else if(gamechio.equals("ow")||gamechio.equals("OW")){
            return "redirect:ow";
        }else{
            model.addAttribute("norace", "暂时没有该游戏的赛事信息！");
            return "index";
        }

    }

    @GetMapping("404")
    public String page404(HttpSession session, Model model){
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("useravatar", user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
        }else{
            model.addAttribute("logstate",0);
        }
        return "404";
    }

    @GetMapping("/login")
    public String login(HttpSession session, HttpServletResponse response, HttpServletRequest request, Model model, @RequestParam(value = "backto",required = false,defaultValue = "/index") String backto) throws IOException{
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            //已验证身份的用户
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String scriptinfo = "<script>alert(\"You have signed in, back to index\");window.location.href='index';</script>";
            //将数据直接发送到客户端浏览器
            response.getWriter().print(scriptinfo);
            return null;
        }else{
            session.setAttribute("backto",backto);
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(String username, String userpwd,Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        boolean result = service.login(username, userpwd);
        if(result){
            session.setAttribute("username", username);
            session.setAttribute("userpwd", userpwd);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss SSS");
            String strDate =dateFormat.format(new Date());
            session.setAttribute("logintime", strDate);
            session.setAttribute("loginip", request.getRemoteAddr());
            String backto = (String) session.getAttribute("backto");
            return ("redirect:" + backto);
        }else{
            model.addAttribute("logininfo", "Incorrect login info");
            return "login";
        }
    }

    @GetMapping("/retrievepwd")
    public String retrievepwd(){
        return "retrievepwd";
    }

    @PostMapping("/retrievepwd")
    public String retrievepwd(String email, Model model, String code, String bt, HttpSession session){
        if(bt.equals("bt1")){
            if(email==null||email==""){
                //input nothing
                model.addAttribute("senderror","please enter your email");
                return "retrievepwd";
            }else{
                //input an email
                if(service.findbyemail(email)==null){
                    //input wrong email
                    model.addAttribute("senderror","no account's been created by this email, please check!");
                    return "retrievepwd";
                }else{
                    // input email correctly
                    if(sendemail.sendMimeMail(email,session)==false){ //session.addAttribute (email) &(code)
                        // send email failed
                        model.addAttribute("senderror","send email failed");
                        return "retrievepwd";
                    }else{
                        //send email successfully
                        model.addAttribute("sendsuccess","send email successfully");
                        model.addAttribute("email",email);
                        return "retrievepwd";
                    }
                }
            }
        }else if(bt.equals("bt2")){
            String real_code = (String) session.getAttribute("code");
            if(code==null||code==""){
                //user enter nothing
                model.addAttribute("verifyerror","please enter your code");
                model.addAttribute("email",email);
                return "retrievepwd";
            }else if(!real_code.equals(code)){
                //user enter wrong code
                model.addAttribute("verifyerror","verification failed");
                model.addAttribute("email",email);
                return "retrievepwd";
            }else{
                //user enter right code
                session.setAttribute("verify_resetpwd","success");
                session.setAttribute("username",service.findbyemail(email).getUser_name());
                model.addAttribute("email",email);
                return "redirect:resetpwd";
            }
        }else{
            return "retrievepwd";
        }

    }

    @GetMapping("/resetpwd")
    public String resetpwd(HttpSession session, HttpServletResponse response,Model model) throws IOException {
        if(session.getAttribute("verify_resetpwd").equals("success")){
            model.addAttribute("username",(String)session.getAttribute("username"));
            return "resetpwd";
        }else{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String scriptinfo = "<script>alert(\"Please verify your email first!\");window.location.href='retrievepwd';</script>";
            //将数据直接发送到客户端浏览器
            response.getWriter().print(scriptinfo);
            return null;
        }
    }

    @PostMapping("/resetpwd")
    public String resetpwd(String newpwd1, String newpwd2, String bt, Model model, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException{
        if((newpwd1==null||newpwd1=="")||(newpwd2==null||newpwd2=="")){
            model.addAttribute("reseterror","cant be empty");
            model.addAttribute("newpwd1",newpwd1);
            model.addAttribute("newpwd2",newpwd2);
            return "resetpwd";
        }else{
            if(newpwd1.equals(newpwd2)){
                String newpwd = newpwd1;
                String username = (String)session.getAttribute("username");
                service.resetpwd(username, newpwd);
                if (bt.equals("bt1")) {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/html;charset=utf-8");
                    String scriptinfo = "<script>alert(\"Recover account successfully！\");window.location.href='login';</script>";
                    //将数据直接发送到客户端浏览器
                    response.getWriter().print(scriptinfo);
                    return null;
                } else {
                    session.setAttribute("userpwd", newpwd);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss SSS");
                    String strDate =dateFormat.format(new Date());
                    session.setAttribute("logintime", strDate);
                    session.setAttribute("loginip", request.getRemoteAddr());
                    return "redirect:/index";
                }
            } else{
                model.addAttribute("reseterror", "passwords dont match");
                model.addAttribute("newpwd1",newpwd1);
                model.addAttribute("newpwd2",newpwd2);
                return "resetpwd";
            }
        }

    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/register")
    public String register(String email, String code, String bt,HttpSession session, Model model){
        if(bt.equals("bt1")){
            if(email==null||email==""){
                // iuput nothing
                model.addAttribute("senderror","please enter your email");
                return "register";
            }else{
                //input an email
                boolean emailinuse = service.emailexist(email);
                if(emailinuse){
                    // email is in use
                    model.addAttribute("senderror","the email address's in use");
                    return "register";
                }else{
                    // email can be used
                    if(sendemail.sendMimeMail(email,session)==false){
                        //send email failed
                        model.addAttribute("senderror","send email failed");
                        return "register";
                    }else{
                        //send email successfully
                        model.addAttribute("sendsuccess","send email successfully");
                        model.addAttribute("email",email);
                        return "register";
                    }
                }
            }
        }else if(bt.equals("bt2")){
            String real_code = (String) session.getAttribute("code");
            if(code==null||code==""){
                //user did enter nothing
                model.addAttribute("verifyerror","please enter your code!");
                model.addAttribute("email",email);
                return "register";
            }else if(!real_code.equals(code)){
                //user enter wrong code
                model.addAttribute("verifyerror","verification failed!");
                model.addAttribute("email",email);
                return "register";
            }else{
                //user enter right code
                session.setAttribute("verify","success");
                model.addAttribute("email",email);
                return "redirect:createaccount";
            }
        }else{
            return "register";
        }
    }

    @GetMapping("/createaccount")
    public String register2(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("verify")!=null){
            return "createaccount";
        }else{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String scriptinfo = "<script>alert(\"Please verify your email first!\");window.location.href='register';</script>";
            //将数据直接发送到客户端浏览器
            response.getWriter().print(scriptinfo);
            return null;
        }
    }

    @PostMapping("/createaccount")
    public String register2(String username, String userpwd1, String userpwd2, Model model, String bt, HttpServletResponse response, HttpSession session, HttpServletRequest request)throws IOException{
        //要求用户提交的每一项不能为空if-1
        if((username==null||username=="")||(userpwd1==null||userpwd1=="")||(userpwd2==null||userpwd2=="")){
            model.addAttribute("createerror","Cannot be empty！");
            model.addAttribute("username",username);
            model.addAttribute("userpwd1",userpwd1);
            model.addAttribute("userpwd2",userpwd2);
            return "createaccount";
        }else{
            //先判断用户名是否存在if-2
            boolean userexist = service.signup(username);
            if(userexist){
                //存在
                model.addAttribute("createerror", "This username's already in use!");
                model.addAttribute("userpwd1",userpwd1);
                model.addAttribute("userpwd2",userpwd2);
                return "createaccount";
            }else{
                //不存在
                //判断两次密码是否相同if-3
                if(userpwd1.equals(userpwd2)){
                    //密码相同
                    int id= service.count();
                    id++;
                    TUserUser newuser = new TUserUser();
                    newuser.setUse_id(id);
                    newuser.setUser_name(username);
                    newuser.setUser_email((String) session.getAttribute("email"));
                    newuser.setUser_pwd(userpwd1);
                    service.insert(newuser);
                    //用户选择跳转到登陆页手动登录或者自动登录if-4
                    if (bt.equals("bt1")) {
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("text/html;charset=utf-8");
                        String scriptinfo = "<script>alert(\"Create account successfully!\");window.location.href='login';</script>";
                        //将数据直接发送到客户端浏览器
                        response.getWriter().print(scriptinfo);
                        return null;
                    } else {
                        //注册并自动登录
                        session.setAttribute("username", username);
                        session.setAttribute("userpwd", userpwd1);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss SSS");
                        String strDate =dateFormat.format(new Date());
                        session.setAttribute("logintime", strDate);
                        session.setAttribute("loginip", request.getRemoteAddr());
                        return "redirect:/index";
                    }
                }else{
                    //密码不同
                    model.addAttribute("username",username);
                    model.addAttribute("userpwd1",userpwd1);
                    model.addAttribute("createerror", "passwords dont match!");
                    return "createaccount";
                }
            }
        }
    }





    @PostMapping("/profile")
    public String myaccount(Model model, HttpSession session, String userpwd, String email, String userpaid, String userphnum, Date userbirthday){
        if((email==null||email=="")||(userpwd==null||userpwd=="")) {
            String username = (String)session.getAttribute("username");
            TUserUser info = service.findbyusername(username);
            model.addAttribute("username", username);
            model.addAttribute("userpwd", info.getUser_pwd());
            model.addAttribute("email", info.getUser_email());
            model.addAttribute("userpaid", info.getUser_paid());
            model.addAttribute("userphnum" ,info.getUser_phnum());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthday = sdf.format(info.getUser_birthday());
            model.addAttribute("userbirthday", birthday);
            model.addAttribute("accounterror", "密码或邮箱不能为空,更改失败...");
            return "myaccount";
        }else {
            TUserUser updateuser = new TUserUser();
            updateuser.setUser_name((String)session.getAttribute("username"));
            updateuser.setUser_pwd(userpwd);
            updateuser.setUser_email(email);
            updateuser.setUser_paid(userpaid);
            updateuser.setUser_phnum(userphnum);
            updateuser.setUser_birthday(userbirthday);
            service.update(updateuser);
            model.addAttribute("updateok", "更改成功！");
            //再次显示
            String username = (String)session.getAttribute("username");
            TUserUser info = service.findbyusername(username);
            model.addAttribute("username", username);
            model.addAttribute("userpwd", info.getUser_pwd());
            model.addAttribute("email", info.getUser_email());
            model.addAttribute("userpaid", info.getUser_paid());
            model.addAttribute("userphnum" ,info.getUser_phnum());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthday = sdf.format(info.getUser_birthday());
            model.addAttribute("userbirthday", birthday);
            return "myaccount";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, Model model, HttpServletResponse response)throws IOException{
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            //已登录的用户
            //退出清理性工作
            session.removeAttribute("username");
            session.removeAttribute("userpwd");
            session.removeAttribute("logintime");
            session.removeAttribute("loginip");
            return "redirect:index";
        }else{
            //未登录用户，提示其登录
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            String scriptinfo = "<script>alert(\"未登录，请先登录...\");window.location.href='login';</script>";
            //将数据直接发送到客户端浏览器
            response.getWriter().print(scriptinfo);
            return null;
        }
    }




}
