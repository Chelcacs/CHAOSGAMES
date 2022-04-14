package cn.gary.models;

public class UserLikeList {
    private int like_id;
    private String user_name;
    private String video_cid;
    private int like_state;

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getVideo_cid() {
        return video_cid;
    }

    public void setVideo_cid(String video_cid) {
        this.video_cid = video_cid;
    }

    public int getLike_state() {
        return like_state;
    }

    public void setLike_state(int like_state) {
        this.like_state = like_state;
    }

}
