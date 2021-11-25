package ie.whichoperator.whichoperator;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class LeaderboardActivity extends AppCompatActivity {
    LeaderBoardClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new LeaderBoardClient(BuildConfig.USERNAME, BuildConfig.PASSWORD, BuildConfig.URL);
    }

    @SuppressLint("NewApi")
    public void populateLeaderboard() {
        LeaderboardPlayer[] leaderboard;
        try {
            leaderboard = client.getLeaderboard();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}