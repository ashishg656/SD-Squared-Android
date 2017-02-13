package ashish.sdsquaredashish.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import ashish.sdsquaredashish.BR;
import ashish.sdsquaredashish.R;
import ashish.sdsquaredashish.extras.AppConstants;
import ashish.sdsquaredashish.objects.HomeApiResponseObject;
import ashish.sdsquaredashish.utils.UIUtils;

/**
 * Created by Ashish on 12/02/17.
 */

public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<HomeApiResponseObject.Users> mData;
    boolean isMoreAllowed;

    LayoutInflater layoutInflater;
    int marginForImageView;
    int deviceWidth;

    private int lastPosition = -1;

    public HomeListAdapter(Context context, List<HomeApiResponseObject.Users> mData, boolean isMoreAllowed) {
        this.context = context;
        this.mData = mData;
        this.isMoreAllowed = isMoreAllowed;

        layoutInflater = LayoutInflater.from(context);
        marginForImageView = context.getResources().getDimensionPixelSize(R.dimen.z_margin_small);
        deviceWidth = UIUtils.getDeviceWidth(context);
    }

    public void addData(List<HomeApiResponseObject.Users> users, boolean isMoreAllowed) {
        this.isMoreAllowed = isMoreAllowed;
        if (users != null) {
            this.mData.addAll(users);
        }

        notifyDataSetChanged();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

//    class HolderNormal extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.home_user_image)
//        ImageView userImage;
//        @BindView(R.id.home_user_name)
//        TextView userName;
//        @BindView(R.id.home_items_flowlayout)
//        FlowLayout flowLayout;
//        @BindView(R.id.home_item_image)
//        ImageView itemImage;
//
//        public HolderNormal(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//
//        public void setData(HomeApiResponseObject.Users user) {
//            if (user != null) {
//                userName.setText(user.getName());
//
//                if (!AndroidUtils.isEmpty(user.getImage())) {
//                    ImageRequestManager.requestImage(userImage, user.getImage());
//                    userImage.setVisibility(View.VISIBLE);
//                } else {
//                    userImage.setVisibility(View.GONE);
//                }
//
//                flowLayout.removeAllViews();
//
//                performImageRelatedTask(user);
//            }
//        }
//
//        private void performImageRelatedTask(HomeApiResponseObject.Users user) {
//            if (user.getItems().size() == 0) {
//                flowLayout.setVisibility(View.GONE);
//                itemImage.setVisibility(View.GONE);
//            } else if (user.getItems().size() % 2 == 0) {
//                // even images
//                itemImage.setVisibility(View.GONE);
//
//                flowLayout.setVisibility(View.VISIBLE);
//                setGridImages(user.getItems());
//            } else {
//                // odd images
//                itemImage.setVisibility(View.VISIBLE);
//                ImageRequestManager.requestImage(itemImage, user.getItems().get(0));
//
//                if (user.getItems().size() == 1) {
//                    flowLayout.setVisibility(View.GONE);
//                } else {
//                    flowLayout.setVisibility(View.VISIBLE);
//                    setGridImages(user.getItems().subList(1, user.getItems().size()));
//                }
//            }
//
//            if (itemImage.getVisibility() == View.VISIBLE) {
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemImage.getLayoutParams();
//                int width = deviceWidth - 4 * marginForImageView;
//                params.width = width;
//                params.height = width;
//                itemImage.setLayoutParams(params);
//            }
//        }
//
//        private void setGridImages(List<String> strings) {
//            for (int i = 0; i < strings.size(); i++) {
//                ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.home_list_subitem_imageview, flowLayout, false);
//                FlowLayout.LayoutParams params = (FlowLayout.LayoutParams) imageView.getLayoutParams();
//                if (i % 2 == 0) {
//                    // margin left = 0, margin right = 8
//                    params.leftMargin = 0;
//                    params.rightMargin = marginForImageView;
//                } else {
//                    // margin left = 8, margin right = 0
//                    params.leftMargin = marginForImageView;
//                    params.rightMargin = 0;
//                }
//                params.bottomMargin = 2 * marginForImageView;
//
//                int width = (deviceWidth - 6 * marginForImageView) / 2;
//                params.width = width;
//                params.height = width;
//
//                imageView.setLayoutParams(params);
//
//                ImageRequestManager.requestImage(imageView, strings.get(i));
//
//                flowLayout.addView(imageView);
//            }
//        }
//    }

    class HolderProgress extends RecyclerView.ViewHolder {

        public HolderProgress(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AppConstants.TYPE_RECYCLER_VIEW_NORMAL) {
            return new BindingHolder(layoutInflater.inflate(R.layout.home_list_item_layout, parent, false));
        } else {
            return new HolderProgress(layoutInflater.inflate(R.layout.loading_more_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holderCom, int position) {
        position = holderCom.getAdapterPosition();
        if (getItemViewType(position) == AppConstants.TYPE_RECYCLER_VIEW_NORMAL) {
            BindingHolder holder = (BindingHolder) holderCom;

            holder.getBinding().setVariable(BR.user, mData.get(position));
            holder.getBinding().executePendingBindings();

            setAnimation(holder.itemView, position);
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_item_anim);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder) {
        if (holder != null && holder.itemView != null) {
            holder.itemView.clearAnimation();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mData.size()) {
            return AppConstants.TYPE_RECYCLER_VIEW_NORMAL;
        } else {
            return AppConstants.TYPE_RECYCLER_VIEW_LOADING;
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;

        if (mData == null) {
            return size;
        } else {
            size = mData.size();
            if (isMoreAllowed) {
                size++;
            }
        }

        return size;
    }
}
