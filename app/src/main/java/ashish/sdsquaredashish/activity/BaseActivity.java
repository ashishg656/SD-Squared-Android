package ashish.sdsquaredashish.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Created by Ashish on 04/06/16.
 */
public class BaseActivity extends AppCompatActivity {

    Toast toast;

    LinearLayout progressLayout, errorLayout;
    ProgressBar progressBarLoading;
    Button retryButton;

    public void makeToast(String message) {
        if (!isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }

            if (message != null && message.length() > 0) {
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void makeToastLong(String message) {
        if (!isFinishing()) {
            if (toast != null) {
                toast.cancel();
            }

            if (message != null && message.length() > 0) {
                toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    public Context getContext() {
        return BaseActivity.this;
    }

    public AppCompatActivity getActivity() {
        return BaseActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}