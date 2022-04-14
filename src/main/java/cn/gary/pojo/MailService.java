package cn.gary.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;//一定要用@Autowired


    //application.properties中已配置的值
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给前端输入的邮箱，发送验证码
     * @param email
     * @param session
     * @return
     */
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("Welcome to ChaosGames");//主题
            //生成随机数
            String code = randomCode();

            //将随机数放置到session中
            session.setAttribute("email",email);
            session.setAttribute("code",code);

            mailMessage.setText("Your varification code is:"+code);//内容

            mailMessage.setTo(email);//发给谁

            mailMessage.setFrom(from);//你自己的邮箱

            mailSender.send(mailMessage);//发送
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}
