package cn.gary.service;

import cn.gary.dao.UserLikeListDao;
import cn.gary.models.UserLikeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLikeListService {

    @Autowired
    UserLikeListDao dao;

    public UserLikeList findByUsernameAndVideocid(String user_name, String video_cid){
        UserLikeList like_info = dao.findByUsernameAndVideocid(user_name,video_cid);
        return like_info;
    }

    public int insertLike(UserLikeList new_likelist){
        int result = dao.insertLike(new_likelist);
        return result;
    }
    public int count() {
        return dao.count();
    }
}
