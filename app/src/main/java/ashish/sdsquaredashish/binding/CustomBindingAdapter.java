package ashish.sdsquaredashish.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.wefika.flowlayout.FlowLayout;

import java.util.List;

import ashish.sdsquaredashish.serverApi.ImageRequestManager;

/**
 * Created by Ashish on 13/02/17.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"bind:homeFlowLayoutItems"})
    public static void loadImagesInHomeFlowLayout(FlowLayout flowLayout, List<String> items) {

    }

    @BindingAdapter({"bind:setImage"})
    public static void loadImage(ImageView imageView, String url) {
        ImageRequestManager.requestImage(imageView, url);
    }
}
