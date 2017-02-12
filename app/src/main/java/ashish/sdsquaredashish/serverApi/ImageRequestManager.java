package ashish.sdsquaredashish.serverApi;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import ashish.sdsquaredashish.R;

public class ImageRequestManager {

    static ImageRequestManager mInstance;

    public static ImageRequestManager get(Context context) {
        if (mInstance == null)
            mInstance = new ImageRequestManager();
        return mInstance;
    }

    public static void requestImage(final ImageView imageView,
                                    final String imgUrl) {
        if (imageView == null) {
            return;
        } else if (imgUrl == null || imgUrl.length() == 0) {
            try {
                imageView.setImageResource(R.drawable.placeholder);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.placeholder)
                .showImageOnFail(R.drawable.error_image)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();

        ImageLoader.getInstance().displayImage(imgUrl, imageView, options,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap bitmap) {
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                            imageView.setBackgroundColor(0);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        super.onLoadingCancelled(imageUri, view);
                    }
                });
    }

    public static void requestImageWithCallbacks(final Context context, final ImageView imageView,
                                                 final String imgUrl, final RequestBitmap requestBitmap) {
        if (imageView == null) {
            return;
        } else if (imgUrl == null || imgUrl.length() == 0) {
            imageView.setImageResource(R.drawable.placeholder);
            return;
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .showImageOnLoading(R.drawable.placeholder)
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();

        ImageLoader.getInstance().displayImage(imgUrl, imageView, options,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap bitmap) {
                        requestBitmap.onRequestCompleted(bitmap);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        super.onLoadingCancelled(imageUri, view);
                    }
                });
    }

    public interface RequestBitmap {
        void onRequestCompleted(Bitmap bitmap);
    }
}
