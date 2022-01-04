package com.yc.mpmvcboot.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class PhotoUtils {
    /**
     * 重新生成图片宽、高
     * @param srcPath 图片路径
     * @param destPath 新生成的图片路径
     * @param newWith 新的宽度
     * @param newHeight 新的高度
     * @param forceSize 是否强制使用指定宽、高,false:会保持原图片宽高比例约束
     * @return
     * @throws IOException
     */
    public static boolean resizeImage (String srcPath, String destPath, int newWith, int newHeight, boolean forceSize) throws IOException, IOException {
        if (forceSize) {
            Thumbnails.of(srcPath).forceSize(newWith, newHeight).toFile(destPath);
        } else {
            Thumbnails.of(srcPath).width(newWith).height(newHeight).toFile(destPath);
        }
        return true;
    }
}
