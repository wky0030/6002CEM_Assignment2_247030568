package com.forsale.mini.controller;

import com.forsale.mini.dao.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileController {
    private final static Logger log = LoggerFactory.getLogger(MiniLoginController.class);


    @GetMapping("/index") //根据application.properties添加的配置，查找index文件
    public String index(){
        return "home"; //当浏览器输入/index时，会返回 /templates/home.html页面
    }

    //上传文件
    @Value("${file.uploadDir}") //@Value注解读取配置文件中reggie的值
    private String Basepath;

    @CrossOrigin(origins = "*")
    @PostMapping("/upload")
    public R upload(MultipartFile  file) {
        Integer userId=10;
        String des="ds";
        log.error("我是 upload 添加"+userId+" file="+file);
        //file是一个指定文件，必须转存到指定位置，这里的形参必须命名为file！
//        log.info(file.toString());

        String usedName = file.getOriginalFilename();//原始名

        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd");
        System.out.println( sdf.format(d)+"  sss" +d.getTime());

        //创建一个目录对象
        File dir = new File(Basepath);
        //如果目录不存在
        if (!dir.exists()) {
            dir.mkdirs();//就按照Basepath创建一个目录
        }
        //创建一个子目录对象
//        File file2 = new File(Basepath+File.separator+userId);
//        //如果目录不存在
//        if (!file2.exists()) {
//            file2.mkdirs();//就按照Basepath创建一个目录
//        }

        String uuidName="" ;
        String mubiaoFileName="";
        //将临时文件转存到电脑硬盘
        try {
            String suffix = usedName.substring(usedName.lastIndexOf("."));
            uuidName=d.getTime() + UUID.randomUUID().toString() +suffix;//使用时间戳生成文件名
             mubiaoFileName=Basepath +uuidName;
            log.error("我是 upload 添加mubiaoFile="+mubiaoFileName);
            File mubiaoFile=new File(mubiaoFileName);
            mubiaoFile.deleteOnExit();
            file.transferTo(new File(mubiaoFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok().data("success",uuidName);//把文件名字给前端，回显数据
    }

}
