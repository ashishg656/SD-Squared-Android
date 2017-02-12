package ashish.sdsquaredashish.extras;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ashish on 01/09/16.
 */
public class GridViewItemDecoration extends RecyclerView.ItemDecoration {

    int marginLeft;

    public GridViewItemDecoration(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (position % 2 == 0) {
            outRect.set(marginLeft, 0, marginLeft / 2, 0);
        } else {
            outRect.set(marginLeft / 2, 0, marginLeft, 0);
        }
    }
}
