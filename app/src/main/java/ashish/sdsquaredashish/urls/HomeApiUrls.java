package ashish.sdsquaredashish.urls;

/**
 * Created by Ashish on 28/10/16.
 */

public class HomeApiUrls extends AppUrls {

    public static String getHomeApiUrl(int start, int pageSize) {
        return BASE_URL + "users?offset=" + start + "&limit=" + pageSize;
    }
}
