package com.android.lq.p2p.lili.base;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.android.lq.p2p.lili.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ImageConfiguration {

    private static ImageConfiguration configuration;

    private ImageConfiguration() {
    }

    public synchronized static ImageConfiguration newInstance() {
        synchronized (ImageConfiguration.class) {
            if (configuration == null) {
                configuration = new ImageConfiguration();
            }
        }
        return configuration;
    }

    public interface JWImageLoadingListener {
        void onLoadingStarted();

        void onLoadingFailed(String failMessage);

        void onLoadingComplete(Bitmap loadedImage);

        void onLoadingCancelled();
    }

    private JWImageLoadingListener listener;

    public void setJWImageLoadingListener(JWImageLoadingListener listener) {
        this.listener = listener;
    }

    /**
     * 配置ImageLoaderConfiguration
     *
     * @param context 上下文
     */
    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();//开始构建
        ImageLoader.getInstance().init(config);//全局初始化此配置

       /* ImageConfiguration config = new ImageConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建*/
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        DisplayImageOptions options = builder
                //TODO 需要替换掉
                .showImageOnLoading(R.mipmap.app_logo) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.app_logo)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.app_logo)  //设置图片加载/解码过程中错误时候显示的图片
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options OptionsdecodingOptions)//设置图片的解码配置
//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//设置图片加入缓存前，对bitmap进行设置
//.preProcessor(BitmapProcessor preProcessor)
//                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
//                .displayer(new CircleBitmapDisplayer())
                .build();//构建完成
        return options;
    }

    /**
     * 显示图片
     */
    public static void displayImage(ImageLoader imageLoader, String url, ImageView imageView, DisplayImageOptions options, JWImageLoadingListener listener) {
        display(imageLoader, url, imageView, options, listener);
    }

    private static void display(ImageLoader imageLoader, String url, ImageView imageView, DisplayImageOptions options, final JWImageLoadingListener listener) {
        imageLoader.displayImage(url, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {//开始加载的时候执行
                if (listener != null) {
                    listener.onLoadingStarted();
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {//加载失败的时候执行
                if (listener != null) {
                    listener.onLoadingFailed(failReason.toString());
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {//加载成功的时候执行
                if (listener != null) {
                    listener.onLoadingComplete(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {//加载取消的时候执行
                if (listener != null) {
                    listener.onLoadingCancelled();
                }
            }
        });
    }
}
