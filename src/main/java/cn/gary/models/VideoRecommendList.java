package cn.gary.models;

public class VideoRecommendList {
    private int recommend_id;
    private String video_cid;
    private String rc_video_cid;

    public int getRecommend_id() {
        return recommend_id;
    }

    public void setRecommend_id(int recommend_id) {
        this.recommend_id = recommend_id;
    }

    public String getVideo_cid() {
        return video_cid;
    }

    public void setVideo_cid(String video_cid) {
        this.video_cid = video_cid;
    }

    public String getRc_video_cid() {
        return rc_video_cid;
    }

    public void setRc_video_cid(String rc_video_cid) {
        this.rc_video_cid = rc_video_cid;
    }


}
