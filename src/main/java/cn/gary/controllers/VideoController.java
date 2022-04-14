package cn.gary.controllers;

import cn.gary.models.TUserUser;
import cn.gary.models.UserLikeList;
import cn.gary.models.VideoRecommendList;
import cn.gary.models.VideoRecord;
import cn.gary.service.TUserUserService;
import cn.gary.service.UserLikeListService;
import cn.gary.service.VideoRecommendListService;
import cn.gary.service.VideoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/")
public class VideoController {

    @Autowired
    VideoRecordService video_service;

    @Autowired
    TUserUserService service;

    @Autowired
    UserLikeListService like_service;

    @Autowired
    VideoRecommendListService recommend_service;

    @GetMapping("videopage")
    public String videopage(HttpSession session, Model model){
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("useravatar",user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
        }else{
            model.addAttribute("logstate",0);
        }
        //大屏视频对象
        VideoRecord record_1 = video_service.findByVideoid(1);
        model.addAttribute("record_1",record_1);
        //下面的很多视频对象
        model.addAttribute("record_videoshow_1",video_service.findByVideoid(2));
//        model.addAttribute("record_videoshow_2",video_service.findByVideoid(2));
//        model.addAttribute("record_videoshow_3",video_service.findByVideoid(3));
//        model.addAttribute("record_videoshow_4",video_service.findByVideoid(4));
//        model.addAttribute("record_videoshow_5",video_service.findByVideoid(5));
//        model.addAttribute("record_videoshow_6",video_service.findByVideoid(6));
//        model.addAttribute("record_videoshow_7",video_service.findByVideoid(7));
//        model.addAttribute("record_videoshow_8",video_service.findByVideoid(8));
//        model.addAttribute("record_videoshow_9",video_service.findByVideoid(9));
//        model.addAttribute("record_videoshow_10",video_service.findByVideoid(10));
//        model.addAttribute("record_videoshow_11",video_service.findByVideoid(11));
//        model.addAttribute("record_videoshow_12",video_service.findByVideoid(12));
//        model.addAttribute("record_videoshow_13",video_service.findByVideoid(13));
//        model.addAttribute("record_videoshow_14",video_service.findByVideoid(14));
//        model.addAttribute("record_videoshow_15",video_service.findByVideoid(15));
//        model.addAttribute("record_videoshow_16",video_service.findByVideoid(16));
//        model.addAttribute("record_videoshow_17",video_service.findByVideoid(17));
//        model.addAttribute("record_videoshow_18",video_service.findByVideoid(18));
//        model.addAttribute("record_videoshow_19",video_service.findByVideoid(19));
//        model.addAttribute("record_videoshow_20",video_service.findByVideoid(20));
        return "videopage";
    }
    @PostMapping("videopage")
    public String videopage(String videoaddress, String coveraddress){
        VideoRecord new_video = new VideoRecord();
        new_video.setVideo_id(video_service.count()+1);
        new_video.setVideo_cid("JUHUNlOhBnH");
        new_video.setVideo_address(videoaddress);
        new_video.setVideo_cover_address(coveraddress);
        video_service.insert(new_video);
        return null;
    }

    @GetMapping("bigplayer")
    public String bigplayer(@RequestParam("cid") String video_cid, Model model){
        VideoRecord record_1 = video_service.findByVideocid(video_cid);
        model.addAttribute("record_1",record_1);
        return "bigplayer";
    }

    @GetMapping("videos/watch")
    public String watch(@RequestParam("cid") String video_cid, Model model, HttpSession session){
        if((session.getAttribute("username")!=null)&&(session.getAttribute("userpwd")!=null)){
            model.addAttribute("logstate",1);
            String username = (String)session.getAttribute("username");
            TUserUser user_info = service.findbyusername(username);
            model.addAttribute("useravatar",user_info.getUser_avatar());
            model.addAttribute("username",user_info.getUser_name());
        }else{
            model.addAttribute("logstate",0);
        }
        //通过url地址中的cid的值查找对应的视频对象
        VideoRecord record_video = video_service.findByVideocid(video_cid);
        //根据视频对象中的作者名找到视频发布者的用户对象
        String author_username = record_video.getVideo_author();
        TUserUser this_author = service.findbyusername(author_username);
        //根据视频cid查找对应推荐记录集合，取到推荐视频的cid，转化成推荐视频集合
        List<VideoRecommendList> recommend_video = recommend_service.findByVideocid(video_cid);
        //初始化推荐视频集合
        List<VideoRecord> rc_record_video = new ArrayList<VideoRecord>(); //rc_record_video为推荐视频对象的列表
        for(int i=0; i<recommend_video.size(); i++){
            String rc_video_cid = recommend_video.get(i).getRc_video_cid();
            VideoRecord rc_video = video_service.findByVideocid(rc_video_cid);
            rc_record_video.add(rc_video);
        }
        int video_num = rc_record_video.size();
        Random random = new Random();
        if(video_num<10){
            //因为视频播放页显示10条相关的推荐视频，当推荐视频少于10条,集合加入视频直到10条
            for(int i=0; i<(10-video_num); i++){
                // !暂时随机[1,10],从数据库video_id等于1到10的记录中挑选视频加入推荐，后续改进推荐算法
                int random_id = random.nextInt(10)+1;
                VideoRecord random_video = video_service.findByVideoid(random_id);
                rc_record_video.add(random_video);
            }
        }else if(video_num>10){
            //因为视频播放页显示10条相关的推荐视频，当推荐视频大于10条,集合删除视频直到10条
            for(int i=0; i<(video_num-10); i++){
                int random_number = random.nextInt(rc_record_video.size()); //删除一个视频后，集合大小-1，随机数范围也-1
                rc_record_video.remove(random_number);
            }
        }
        //根据用户名和视频cid找到点赞等喜欢记录对象
        UserLikeList like_info = like_service.findByUsernameAndVideocid((String)session.getAttribute("username"),video_cid);
        //将4个对象放入model中
        model.addAttribute("record_video", record_video);
        model.addAttribute("this_author",this_author);
        model.addAttribute("like_info",like_info);
        model.addAttribute("rc_record_video",rc_record_video);
        return "videos/watch";
    }

    @PostMapping("videos/watch")
    @ResponseBody
    public String watch(String operation, String video_cid, @RequestParam(value = "like_state",required = false,defaultValue = "2") int like_state, HttpSession session, Model model){
        //当请求为点赞或者点踩
        if(operation.equals("likeORnot")){
            //通过前端ajax传过来的cid从数据库读取对应的视频对象
            VideoRecord record_video = video_service.findByVideocid(video_cid);
            //在java中更新视频对象点赞或者点踩数
            if(like_state==1){
                record_video.setVideo_like_num(record_video.getVideo_like_num()+1);
            }else{
                record_video.setVideo_dislike_num(record_video.getVideo_dislike_num()+1);
            }
            //将更新后的视频对象更新到数据库
            video_service.updateVideoRecord(record_video);
            //添加喜欢记录
            UserLikeList new_likelist = new UserLikeList();
            int list_num = like_service.count();
            new_likelist.setLike_id(list_num+1);
            new_likelist.setUser_name((String)session.getAttribute("username"));
            new_likelist.setVideo_cid(video_cid);
            new_likelist.setLike_state(like_state);
            like_service.insertLike(new_likelist);
            //重新请求视频对象
            VideoRecord new_record_video = video_service.findByVideocid(video_cid);
            model.addAttribute("record_video",new_record_video);
            return null;
        }else if(operation.equals("playcounts")){
            //当请求为增加播放量
            VideoRecord record_video = video_service.findByVideocid(video_cid);
            record_video.setVideo_access_num(record_video.getVideo_access_num()+1);
            video_service.updateVideoRecord(record_video);
            return null;
        }else{
            return null;
        }

    }

}
