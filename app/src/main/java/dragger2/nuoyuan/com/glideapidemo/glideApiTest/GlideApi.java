package dragger2.nuoyuan.com.glideapidemo.glideApiTest;

import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

import dragger2.nuoyuan.com.glideapidemo.R;
import dragger2.nuoyuan.com.glideapidemo.activity.GalleryUseRecyclerViewActivity;
import dragger2.nuoyuan.com.glideapidemo.activity.HardActivity;
import dragger2.nuoyuan.com.glideapidemo.activity.NormalActivity;
import dragger2.nuoyuan.com.glideapidemo.activity.SimpleGalleryActivity;
import dragger2.nuoyuan.com.glideapidemo.utils.CornersTransform;
import dragger2.nuoyuan.com.glideapidemo.utils.CustomLoader;
import dragger2.nuoyuan.com.glideapidemo.utils.CustomModel;
import dragger2.nuoyuan.com.glideapidemo.utils.NYImageView;
import dragger2.nuoyuan.com.glideapidemo.utils.Tools;

/**
 * Created by weichyang on 2017/4/18.
 */

public class GlideApi {


    private RequestManager requestManager;
    private NYImageView imageView;
    public static final String IMG_NAME = "dog.jpg";
    public static final String IMG_NAME_MP4 = "mmm.mp4";
    private String url1 = "http://img0.ph.126.net/SSM85Ibp3g8hOTa2tkjnBw==/6631268976328190694.jpg";
    private String url2 = "http://img1.ph.126.net/tAn2tp-lzI1waAzc1NiN-g==/6631224995863078981.jpg";
    private String url3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492508043125&di=223602d9925243ae9d06ea59d03d5950&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3D195f5319ddc451daf6f60ce386fc52a5%2Febc4b74543a98226cd76e0608a82b9014b90ebc6.jpg";

    public void Click(final NormalActivity activity, int position) {
        if (requestManager == null)
            requestManager = Glide.with(activity);

        imageView = activity.binding.imageView;

        switch (position) {
            case 0: //加载本地资源
                requestManager.load(Tools.resourceIdToUri(activity, R.mipmap.ic_launcher)).into(activity.binding.imageView);
                break;
            case 1: //加载Assets
                File file = new File(activity.getFilesDir(), IMG_NAME);
                if (file.exists())
                    requestManager.load(file).into(activity.binding.imageView);
                break;
            case 2: //加载本地资源
                requestManager.load(R.mipmap.ic_launcher).into(imageView);
                break;
            case 3: //自定义Loader
                requestManager.using(new CustomLoader(activity)).load(new CustomModel() {
                    @Override
                    public String buidUrl(int width, int height) {
                        if (width >= 600) {//在1080p的手机上看到url1
                            return url1;
                        } else {//在720p的手机上看到url2
                            return url2;
                        }
                    }
                }).into(imageView);
                break;
            case 4: //加载Gallery资源
                activity.startActivity(new Intent(activity, SimpleGalleryActivity.class));
                break;
            case 5: //跳转到RecycleView
                activity.startActivity(new Intent(activity, GalleryUseRecyclerViewActivity.class));
                break;
            case 6: //跳转到RecycleView
                activity.startActivity(new Intent(activity, HardActivity.class));
                break;
            case 7: //lOAD gif
                Glide.with(activity).load(url3).asGif().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(activity.binding.gifImag);
                break;
            case 8: //lOAD gif
                File mVideoFile = new File(activity.getExternalCacheDir(), IMG_NAME_MP4);
                Glide.with(activity).load(mVideoFile).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(activity.binding.gifImag);
                break;
            case 9: //清除缓存 (内存 和 sd卡中)
                Glide.get(activity).clearMemory();
                Glide.get(activity).clearDiskCache();
                break;
            case 10: //自定义Gradle Modul
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(activity).clearMemory();
                        Glide.get(activity).clearDiskCache();
                    }
                }).start();
                break;
            case 11: //图片尺寸的压缩
                Glide.with(activity).load(url1).override(100, 100).into(activity.binding.gifImag);
                break;
            case 12: //图片圆角转换
                Glide.with(activity).load(url1).transform(new CornersTransform(activity)).into(activity.binding.gifImag);
                break;
            case 13: //图片裁切
                Glide.with(activity).load(url1).fitCenter().into(activity.binding.gifImag);
                break;
            default:
                break;

        }
    }
}
