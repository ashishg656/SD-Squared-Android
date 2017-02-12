package ashish.sdsquaredashish.requests;

import android.content.Context;

import com.android.volley.Request;

import ashish.sdsquaredashish.application.AppApplication;
import ashish.sdsquaredashish.serverApi.AppRequestListener;
import ashish.sdsquaredashish.serverApi.CustomStringRequest;

/**
 * Created by Ashish on 28/10/16.
 */

public class HomeApiRequests extends AppRequests {

    public static void makeHomeApiRequest(String url, AppRequestListener requestListener, Context context) {
        CustomStringRequest request = new CustomStringRequest(Request.Method.GET, url, HOME_REQUEST_TAG,
                requestListener, null, getHeaders(context));
        AppApplication.getInstance().addToRequestQueue(request, HOME_REQUEST_TAG);
    }
}
