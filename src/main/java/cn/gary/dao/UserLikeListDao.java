package cn.gary.dao;

import cn.gary.models.UserLikeList;

public interface UserLikeListDao {
    UserLikeList findByUsernameAndVideocid(String user_name, String video_cid);
    int insertLike(UserLikeList new_likelist);
    int count();
}
