package ie.whichoperator.whichoperator;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class LeaderboardActivity extends AppCompatActivity {
    LeaderBoardClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new LeaderBoardClient(BuildConfig.USERNAME, BuildConfig.PASSWORD, BuildConfig.URL);
    }
}