package ashish.sdsquaredashish.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import ashish.sdsquaredashish.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ashish on 12/02/17.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_progress)
    ProgressBar progressBar;

    private int splashDuration = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);
        ButterKnife.bind(this);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                openHomeActivity();
            }
        }, splashDuration);
    }

    @Override
    protected void onDestroy() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        super.onDestroy();
    }
}
