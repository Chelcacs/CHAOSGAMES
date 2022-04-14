package cn.gary.dao;

import cn.gary.models.VideoRecommendList;

import java.util.List;

public interface VideoRecommendListDao {
    List<VideoRecommendList> findByVideocid(String video_cid);
}
