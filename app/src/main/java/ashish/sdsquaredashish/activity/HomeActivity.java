package ashish.sdsquaredashish.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.VolleyError;

import ashish.sdsquaredashish.R;
import ashish.sdsquaredashish.adapter.HomeListAdapter;
import ashish.sdsquaredashish.extras.AppConstants;
import ashish.sdsquaredashish.extras.RequestTags;
import ashish.sdsquaredashish.objects.HomeApiResponseObject;
import ashish.sdsquaredashish.requests.HomeApiRequests;
import ashish.sdsquaredashish.serverApi.AppRequestListener;
import ashish.sdsquaredashish.urls.HomeApiUrls;
import ashish.sdsquaredashish.utils.VolleyUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ashish on 12/02/17.
 */

public class HomeActivity extends BaseActivity implements AppRequestListener {

    @BindView(R.id.home_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    int start, pageSize = 10;
    HomeListAdapter adapter;
    LinearLayoutManager layoutManager;
    boolean isMoreAllowed, isRequestRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isMoreAllowed && !isRequestRunning && layoutManager != null && adapter != null) {
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    int totalItemsCount = adapter.getItemCount();

                    if (lastVisibleItem >= totalItemsCount - AppConstants.RECYCLER_VIEW_LOAD_MORE_THRESHOLD) {
                        loadData();
                    }
                }
            }
        });

        setProgressAndErrorLayoutVariables();

        loadData();
    }

    private void loadData() {
        String url = HomeApiUrls.getHomeApiUrl(start, pageSize);
        HomeApiRequests.makeHomeApiRequest(url, this, this);
    }

    @Override
    public void onRequestStarted(String requestTag) {
        if (requestTag.equalsIgnoreCase(RequestTags.HOME_REQUEST_TAG)) {
            isRequestRunning = true;
            if (adapter == null) {
                hideErrorLayout();
                showProgressLayout();
            }
        }
    }

    @Override
    public void onRequestFailed(String requestTag, VolleyError error, boolean networkError) {
        if (requestTag.equalsIgnoreCase(RequestTags.HOME_REQUEST_TAG)) {
            isRequestRunning = false;
            hideProgressLayout();

            if (adapter == null) {
                showErrorLayout();
            } else {
                hideErrorLayout();
                makeToast(getString(R.string.unable_to_load_more));

                adapter.addData(null, false);
            }
        }
    }

    @Override
    public void onRequestCompleted(String requestTag, String response) {
        if (requestTag.equalsIgnoreCase(RequestTags.HOME_REQUEST_TAG)) {
            isRequestRunning = false;
            HomeApiResponseObject mData = (HomeApiResponseObject) VolleyUtils.getResponseObject(response, HomeApiResponseObject.class);
            setData(mData);
        }
    }

    private void setData(HomeApiResponseObject mData) {
        hideErrorLayout();
        hideProgressLayout();

        if (mData == null || !mData.getStatus() || mData.getData() == null || mData.getData().getUsers() == null) {
            // show empty layout
            isMoreAllowed = false;
        } else {
            isMoreAllowed = mData.getData().getHas_more();
            start += pageSize;

            if (adapter != null) {
                adapter.addData(mData.getData().getUsers(), isMoreAllowed);
            } else {
                adapter = new HomeListAdapter(this, mData.getData().getUsers(), isMoreAllowed);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
