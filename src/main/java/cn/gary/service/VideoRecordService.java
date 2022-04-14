package cn.gary.service;


import cn.gary.dao.VideoRecordDao;
import cn.gary.models.TUserUser;
import cn.gary.models.VideoRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoRecordService {

    @Autowired                  //向Spring Container索要
    VideoRecordDao dao;

    public VideoRecord findByVideoid(int videoid){
        VideoRecord record = dao.findByVideoid(videoid);
        return record;
    }

    public VideoRecord findByVideocid(String videocid){
        VideoRecord record = dao.findByVideocid(videocid);
        return record;
    }

    public int updateVideoRecord(VideoRecord new_record){
        int result = dao.updateVideoRecord(new_record);
        return result;
    }

    public int count() {
        return dao.count();
    }

    public int insert(VideoRecord new_video) {
        int result = dao.insert(new_video);
        return result;
    }
}
