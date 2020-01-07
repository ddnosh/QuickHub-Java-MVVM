package com.androidwind.github.module;

import com.androidwind.github.MyApplication;
import com.androidwind.github.module.glide.GlideProcessor;
import com.androidwind.github.module.glide.IImageProcessor;
import com.androidwind.github.module.glide.ImageConfig;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class QuickModule {

    private static IImageProcessor mIImageProcessor;
    private static ImageConfig mImageConfig;

    //配置生效
    public static void launch() {
        //image
        if (mIImageProcessor == null) {
            mIImageProcessor = new GlideProcessor();
        }
        mIImageProcessor.init(MyApplication.getInstance(), mImageConfig);
    }

    //image调用入口
    public static <T extends IImageProcessor> T imageProcessor() {
        if (mIImageProcessor != null) {
            return (T) mIImageProcessor;
        }
        return (T) new GlideProcessor();
    }

    //image配置入口
    public static ImageConfig configImage() {
        return configImage(null);
    }

    //image配置入口
    public static ImageConfig configImage(IImageProcessor processor) {
        if (processor == null) {
            mImageConfig = new ImageConfig();
        }
        else {
            mIImageProcessor = processor;
            mImageConfig = new ImageConfig();
        }
        return mImageConfig;
    }
}
