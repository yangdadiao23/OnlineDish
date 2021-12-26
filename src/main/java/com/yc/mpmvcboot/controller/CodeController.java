package com.yc.mpmvcboot.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class CodeController {

    @Autowired
    private DefaultKaptcha kaptchaProducer;

    @RequestMapping(value = "/kaptcha", method = {RequestMethod.GET, RequestMethod.POST})
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        String text = kaptchaProducer.createText();
        //将文本转变为图片
        BufferedImage image = kaptchaProducer.createImage(text);
        session.setAttribute("kaptcha", text);
        //设置格式
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            //设置输出的图片格式
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
             e.printStackTrace();
        }

    }
}
