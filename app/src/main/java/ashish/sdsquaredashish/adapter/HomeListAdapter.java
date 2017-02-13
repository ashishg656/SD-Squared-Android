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
