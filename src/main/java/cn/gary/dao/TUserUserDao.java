package cn.gary.dao;

import cn.gary.models.TUserUser;

import java.util.List;

/**
 * 数据库访问层
 */
public interface TUserUserDao {
    //find、findAll、select、add、insert
    List<TUserUser> find();
    List<TUserUser> findPage(Integer offset, Integer length);
    int count();
    TUserUser findById(Integer id);
    int deleteById(Integer id);
    int insert(TUserUser user);
    int update(TUserUser user);
    int resetPwd(String username, String newPwd);
    int login(String username, String userpwd);
    TUserUser findbyemail(String email);
    void resetpwd(String username, String newpwd);
    TUserUser findbyusername(String username);
    int signup(String username);
    int emailexist(String email);
}
