package cn.gary.service;

import cn.gary.dao.VideoRecommendListDao;
import cn.gary.models.VideoRecommendList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoRecommendListService {
    @Autowired
    VideoRecommendListDao dao;
    //根据视频cid返回该视频对应的推荐视频的对象集合
    public List<VideoRecommendList> findByVideocid(String video_cid){
        List<VideoRecommendList> recommend_video = dao.findByVideocid(video_cid);
        return recommend_video;
    }

}
