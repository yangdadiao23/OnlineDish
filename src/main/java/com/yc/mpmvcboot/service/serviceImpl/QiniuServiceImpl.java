package com.yc.mpmvcboot.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.yc.mpmvcboot.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

import static org.springframework.core.io.buffer.DataBufferUtils.readInputStream;

@Service
@Slf4j
public class QiniuServiceImpl {


    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "8gAjZPkmW-FCer3LNoHORLDuZmSf24Y_ChLGr2sl";
    String SECRET_KEY = "VJq6XvysebE0GhdwqPELVpb5egdvwwqunWZXJodN";
    // 要上传的空间
    String bucketname = "order-order";

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    // 构造一个带指定Zone对象的配置类,不同的七云牛存储区域调用不同的zone
    Configuration cfg = new Configuration(Zone.zone0());
    // ...其他参数参考类注释
    UploadManager uploadManager = new UploadManager(cfg);

    // 测试域名，只有30天有效期
    private static String QINIU_IMAGE_DOMAIN = "http://r55x0z8s4.hd-bkt.clouddn.com/";

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            // 判断是否是合法的文件后缀
            if (!FileUtils.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            // 调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            // 打印返回的信息
            if (res.isOK() && res.isJson()) {
                // 返回这张存储照片的地址
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                log.error("七牛异常:" + res.bodyString());
                return null;
            }
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            log.error("七牛异常:" + e.getMessage());
            return null;
        }
    }

    public String getDownloadUrl(String targetUrl) {
        String downloadUrl = auth.privateDownloadUrl(targetUrl);
        return downloadUrl;
    }

    public void download(String targetUrl,String fileName) {
        //获取downloadUrl
        String downloadUrl = getDownloadUrl(targetUrl);
        //本地保存路径
        String filePath ="E:/mp-mvc-boot/src/main/resources/static/photo/";
        download(downloadUrl,filePath,fileName);
    }

    public static void download(String url, String filepath,String fileName) {
        OkHttpClient client = new OkHttpClient();
        System.out.println(url);
        Request req = new Request.Builder().url(url).build();
      okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                byte[] data = body.bytes();
                //判断文件夹是否存在，不存在则创建
                File file = new File(filepath);
                if (!file.exists() && !file.isDirectory()) {
                    System.out.println("===文件夹不存在===创建====");
                    file.mkdir();
                }
                File imgFile = new File(filepath+fileName);
                FileOutputStream fops = new FileOutputStream(imgFile);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
    }



}
