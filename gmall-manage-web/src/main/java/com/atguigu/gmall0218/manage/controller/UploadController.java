package com.atguigu.gmall0218.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@CrossOrigin
public class UploadController{

    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file) throws IOException, MyException {
        String ip="http://192.168.232.128";
        String file1 = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(file1);
        TrackerClient trackerClient=new TrackerClient();
        // 获取连接
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename=file.getOriginalFilename();
        String extName= StringUtils.substringAfterLast(orginalFilename,".");
        // 上传图片
        String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
        for (int i = 0; i < upload_file.length; i++) {
            String s = upload_file[i];

			/*
			s = group1
			s = M00/00/00/wKhD2106tuSAY9S9AACGx2c4tJ4084.jpg
			 */
			ip=ip+"/"+s;


        }
        System.out.println(ip);
        return ip;
    }

}
