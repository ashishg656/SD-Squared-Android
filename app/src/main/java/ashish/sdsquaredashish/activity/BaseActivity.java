package ashish.sdsquaredashish.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import ashish.sdsquaredashish.R;

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

    public void setProgressAndErrorLayoutVariables() {
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        retryButton = (Button) findViewById(R.id.button_error);
        progressBarLoading = (ProgressBar) findViewById(R.id.progress_bar_loading);
    }

    public void showProgressLayout() {
        try {
            progressLayout.setVisibility(View.VISIBLE);
            progressBarLoading.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void showErrorLayout() {
        try {
            errorLayout.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void hideProgressLayout() {
        try {
            progressLayout.setVisibility(View.GONE);
            progressBarLoading.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void hideErrorLayout() {
        try {
            errorLayout.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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

    /*
    *
    * activity opening code
    *
    * */

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}