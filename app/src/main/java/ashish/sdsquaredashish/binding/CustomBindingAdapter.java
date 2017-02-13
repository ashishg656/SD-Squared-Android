package ashish.sdsquaredashish.binding;

import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.wefika.flowlayout.FlowLayout;

import java.util.List;

import ashish.sdsquaredashish.R;
import ashish.sdsquaredashish.serverApi.ImageRequestManager;
import ashish.sdsquaredashish.utils.UIUtils;

/**
 * Created by Ashish on 13/02/17.
 */

public class CustomBindingAdapter {

    @BindingAdapter({"bind:homeFlowLayoutItems"})
    public static void loadImagesInHomeFlowLayout(FlowLayout flowLayout, List<String> items) {
        flowLayout.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(flowLayout.getContext());
        int deviceWidth = UIUtils.getDeviceWidth(flowLayout.getContext());
        int marginForImageView = flowLayout.getContext().getResources().getDimensionPixelSize(R.dimen.z_margin_small);

        if (items == null || items.size() == 0) {
            flowLayout.setVisibility(View.GONE);
        } else {
            flowLayout.setVisibility(View.VISIBLE);

            boolean isEven = items.size() % 2 == 0;

            for (int i = 0; i < items.size(); i++) {
                ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.home_list_subitem_imageview, flowLayout, false);
                FlowLayout.LayoutParams params = (FlowLayout.LayoutParams) imageView.getLayoutParams();

                if (i == 0 && !isEven) {
                    // handle big image
                    params.leftMargin = 0;
                    params.rightMargin = 0;

                    int width = deviceWidth - 4 * marginForImageView;
                    params.width = width;
                    params.height = width;
                } else {
                    // small image
                    boolean rightSide = false;
                    if (isEven && (i % 2 != 0)) {
                        rightSide = true;
                    } else if (!isEven && (i % 2 == 0)) {
                        rightSide = true;
                    }

                    if (!rightSide) {
                        // margin left = 0, margin right = 8
                        params.leftMargin = 0;
                        params.rightMargin = marginForImageView;
                    } else {
                        // margin left = 8, margin right = 0
                        params.leftMargin = marginForImageView;
                        params.rightMargin = 0;
                    }

                    int width = (deviceWidth - 6 * marginForImageView) / 2;
                    params.width = width;
                    params.height = width;
                }

                params.bottomMargin = 2 * marginForImageView;
                imageView.setLayoutParams(params);
                ImageRequestManager.requestImage(imageView, items.get(i));
                flowLayout.addView(imageView);
            }
        }
    }

    @BindingAdapter({"bind:setImage"})
    public static void loadImage(ImageView imageView, String url) {
        ImageRequestManager.requestImage(imageView, url);
    }
}
