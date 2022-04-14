package cn.gary.dao;

import cn.gary.models.VideoRecord;

import java.util.List;

public interface VideoRecordDao {
    VideoRecord findByVideoid(Integer videoid);
    VideoRecord findByVideocid(String videocid);
    int updateVideoRecord(VideoRecord new_record);
    int count();
    int insert(VideoRecord new_video);
}
