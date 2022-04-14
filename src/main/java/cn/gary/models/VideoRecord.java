package cn.gary.models;

public class VideoRecord {
    private int video_id;
    private String video_cid;
    private String video_cover_address;
    private String video_address;
    private String video_author;
    private String video_upload_time;
    private int video_access_num;
    private int video_like_num;
    private int video_dislike_num;
    private int video_comment_num;
    private String video_description;
    private String video_title;

    public int getVideo_like_num() {
        return video_like_num;
    }

    public void setVideo_like_num(int video_like_num) {
        this.video_like_num = video_like_num;
    }



    public int getVideo_access_num() {
        return video_access_num;
    }

    public void setVideo_access_num(int video_access_num) {
        this.video_access_num = video_access_num;
    }



    public int getVideo_comment_num() {
        return video_comment_num;
    }

    public void setVideo_comment_num(int video_comment_num) {
        this.video_comment_num = video_comment_num;
    }



    public int getVideo_dislike_num() {
        return video_dislike_num;
    }

    public void setVideo_dislike_num(int video_dislike_num) {
        this.video_dislike_num = video_dislike_num;
    }




    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_cid() {
        return video_cid;
    }

    public void setVideo_cid(String video_cid) {
        this.video_cid = video_cid;
    }

    public String getVideo_cover_address() {
        return video_cover_address;
    }

    public void setVideo_cover_address(String video_cover_address) {
        this.video_cover_address = video_cover_address;
    }

    public String getVideo_address() {
        return video_address;
    }

    public void setVideo_address(String video_address) {
        this.video_address = video_address;
    }

    public String getVideo_author() {
        return video_author;
    }

    public void setVideo_author(String video_author) {
        this.video_author = video_author;
    }

    public String getVideo_upload_time() {
        return video_upload_time;
    }

    public void setVideo_upload_time(String video_upload_time) {
        this.video_upload_time = video_upload_time;
    }



}
