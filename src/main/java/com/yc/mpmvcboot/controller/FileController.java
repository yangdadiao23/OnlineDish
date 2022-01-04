package com.yc.mpmvcboot.controller;

import com.yc.mpmvcboot.service.serviceImpl.QiniuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    @Autowired
    private QiniuServiceImpl qiniuService;

    @PostMapping(value = "/fileUpload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        if(file.isEmpty()) {
            return "error";
        }
        try {
            String fileUrl=qiniuService.saveImage(file);
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            String s = UUID.randomUUID().toString();
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            qiniuService.download(fileUrl,fileName);
            model.addAttribute("fileName",fileName);
            return "addHtml";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "forward:/addPhotoHtml";
    }

}
