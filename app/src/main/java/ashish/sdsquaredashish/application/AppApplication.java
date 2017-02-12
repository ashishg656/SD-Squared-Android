package ashish.sdsquaredashish.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import ashish.sdsquaredashish.serverApi.NutraBaseImageDecoder;


/**
 * Created by Ashish on 30/08/16.
 */
public class AppApplication extends Application {

    private static AppApplication sInstance;
    private RequestQueue mRequestQueue;
    public static File cacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        cacheDir = StorageUtils.getIndividualCacheDirectory(this);
        initImageLoader(getApplicationContext());
    }

    public static AppApplication getInstance() {
        if (sInstance == null)
            sInstance = new AppApplication();

        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public void clearAllVolleyCache() {
        try {
            getRequestQueue().getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initImageLoader(Context context) {
        BitmapFactory.Options decodingOptions = new BitmapFactory.Options();

        DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .showImageOnLoading(R.drawable.symphony)
                .decodingOptions(decodingOptions).bitmapConfig(Bitmap.Config.ARGB_8888).build();

        final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        final int cacheSize = 1024 * 1024 * memClass / 8;

        System.out.println("Memory cache size" + cacheSize);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(5).memoryCacheSize(cacheSize)
                .diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(300)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDecoder(new NutraBaseImageDecoder(true)).denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(options).tasksProcessingOrder(QueueProcessingType.FIFO).build();

        ImageLoader.getInstance().init(config);
    }

    public <T> void addToRequestQueue(Request<T> req, @NonNull String tag) {
        RetryPolicy retryPolicy = new DefaultRetryPolicy(8 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        addToRequestQueue(req, tag, true, retryPolicy);
    }

    public <T> void addToRequestQueue(Request<T> req, @NonNull String tag, boolean cache, RetryPolicy retryPolicy) {
        req.setTag(tag);
        req.setShouldCache(cache);
        req.setRetryPolicy(retryPolicy);
        getRequestQueue().add(req);
    }
}
