package com.itheima.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("upload")
public class UpLoadController {


    @GetMapping
    public String toUpload(){
        return "upload";
    }

    @GetMapping("/ups")
    public String toUploads(){
        return "uploads";
    }


    /**
     *上传单个文件
     */
    @PostMapping
    @ResponseBody
    public String uploadFile(MultipartFile file, HttpServletRequest request){

        try {
            //服务端存放路径
            File fileDir=new File("F:\\file");
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }
            //得到名字
            int index = file.getOriginalFilename().lastIndexOf(".");
            String fileSuffix=file.getOriginalFilename().substring(index);
            String fileName=UUID.randomUUID().toString()+fileSuffix;
            File files=new File(fileDir+"/"+fileName);
            //上传
            file.transferTo(files);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

        return "上传成功";
    }

    /**
     *批量文件上传
     */
    @PostMapping("/batch")
    @ResponseBody
    public String uploads(MultipartFile[] file,HttpServletRequest request){

        try {
            //服务端存放路径
            File fileDir=new File("F:\\file");
            if (!fileDir.exists()){
                fileDir.mkdirs();
            }

            for (MultipartFile mfile : file) {
                //得到名字
                int index = mfile.getOriginalFilename().lastIndexOf(".");
                String fileSuffix=mfile.getOriginalFilename().substring(index);
                String fileName=UUID.randomUUID().toString()+fileSuffix;
                File files=new File(fileDir+"/"+fileName);
                //上传
                mfile.transferTo(files);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

        return "全部上传成功";
    }


}
