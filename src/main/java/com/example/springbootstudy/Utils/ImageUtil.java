package com.example.springbootstudy.Utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageUtil {
    public static int getVideoDurationInSeconds(String videoFilePath) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        try {
            grabber.start();
            double durationInSeconds = grabber.getLengthInTime() / 1000000.0;
            return (int) durationInSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                grabber.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public static void saveRandomFrameFromVideo(String videoFilePath, String frameSavePath) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilePath);
        try {
            grabber.start();
            int frameCount = grabber.getLengthInFrames();
            int randomFrameNumber = new Random().nextInt(frameCount);
            grabber.setFrameNumber(randomFrameNumber);
            Frame frame = grabber.grabImage();
            if (frame != null && frame.imageWidth > 0 && frame.imageHeight > 0) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.getBufferedImage(frame, 1.0);
                File output = new File(frameSavePath);
                ImageIO.write(bufferedImage, "jpg", output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                grabber.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/*
    private static final String ROTATE = "rotate";
    private static final String SYMBOL = ".";
    public static String randomGrabberFFmpegImage(String filePath, int mod,String targetFilePath) {
        String targetFilePath = "";
        try{
            FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
            ff.start();
            //图片位置是否正确
            String rotate = ff.getVideoMetadata(ROTATE);
            //获取帧数
            int ffLength = ff.getLengthInFrames();
            Frame f;
            int i = 0;
            //设置截取帧数
            int index = ffLength / mod;
            while (i < ffLength) {
                f = ff.grabImage();
                if(i == index){
                    if (null != rotate && rotate.length() > 1) {
                        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
                        IplImage src = converter.convert(f);
                        f = converter.convert(rotate(src, Integer.parseInt(rotate)));
                    }
                    doExecuteFrame(f, targetFilePath);
                    break;
                }
                i++;
            }
            ff.stop();
        }catch (Exception e){
            System.err.println("获取视频缩略图异常：" + e.getMessage());
        }
        return targetFilePath;
    }
    */
/**
     * 旋转图片
     * @param src
     * @param angle
     * @return
     *//*

    public static IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }
    */
/**
     * 截取缩略图
     * @param f
     * @param targerFilePath:封面图片
     *//*

    public static void doExecuteFrame(Frame f, String targerFilePath) {
        COSClient cosClient = TencentCosUtils.initCosClient();

        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, IMAGEMAT, out);
            // 获取文件流
            InputStream bufferedImage = new ByteArrayInputStream(out.toByteArray());
            int length = out.size();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
            objectMetadata.setContentLength(length);
            // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
            PutObjectRequest putObjectRequest = new PutObjectRequest(TencentCosConfig.bucket, targerFilePath, bufferedImage, objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info("腾讯COS上传视频缩略图成功：{}", putObjectResult.getETag());
            //关闭输入输出流
            bufferedImage.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }
*/

}
