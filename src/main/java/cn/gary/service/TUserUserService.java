package cn.gary.service;

import cn.gary.dao.TUserUserDao;
import cn.gary.models.TUserUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

//集成计算类代码、负责为表示层来调用dao层
@Service                        //将当前类纳入spring管理，实例化入容器
public class TUserUserService {

    @Autowired                  //向Spring Container索要
    TUserUserDao dao;

    public List<TUserUser> find() {
        List<TUserUser> users = dao.find();
        return users;
    }

    public List<TUserUser> findPage(Integer pageIndex, Integer pageSize) {
        Integer offset = (pageIndex - 1) * pageSize;
        List<TUserUser> users = dao.findPage(offset, pageSize);
        return users;
    }

    public int count() {
        return dao.count();
    }

    public int insert(TUserUser user) {
        int result = dao.insert(user);
        return result;
    }

    public int remove(int id) {
        int result = dao.deleteById(id);
        return result;
    }
    //登录
    public boolean login(String username, String userpwd) {
        int result = dao.login(username, userpwd);
        return (result > 0) ? true : false;
    }
    //找回密码
    public TUserUser findbyemail(String email) {
        TUserUser info = dao.findbyemail((email));
        return info;
    }
    //重置密码
    public void resetpwd(String username, String newpwd){
        dao.resetpwd(username, newpwd);
    }
    //查看个人信息
    public TUserUser findbyusername(String username){
        TUserUser info = dao.findbyusername((username));
        return info;
    }
    //注册前查看该用户名是否存在
    public boolean signup(String username){
        int result = dao.signup(username);
        return (result > 0) ? true : false;
    }
    //注册时查看邮箱是否存在
    public boolean emailexist(String email){
        int result = dao.emailexist(email);
        return (result > 0) ? true : false;
    }
    //更新个人信息
    public int update(TUserUser user){
        int result = dao.update(user);
        return result;
    }

}
