package com.pinyougou.shop.controller;

import com.pinyougou.common.util.FastDFSClient;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jax.zou
 * @create 2018-09-15 11:52
 * @desc 图片上传控制器
 **/
@RequestMapping("/upload")
@RestController
public class UploadController {

    @PostMapping
    public Result upload(MultipartFile file){

        Result result = Result.fail("上传失败");

        try {
            //获取文件扩展名
            String file_ext_name = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

            //加载追踪服务地址
            FastDFSClient fastDfsClient = new FastDFSClient("classpath:fastdfs/tracher.conf");

            //上传
            String url = fastDfsClient.uploadFile(file.getBytes(), file_ext_name);

            //返回地址
            result = Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
