package ie.whichoperator.whichoperator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;


public class LeaderboardActivity extends AppCompatActivity {
    LeaderBoardClient client;
    LinearLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        client = new LeaderBoardClient(BuildConfig.USERNAME, BuildConfig.PASSWORD, BuildConfig.URL);
        view = (LinearLayout) findViewById(R.id.linear);
        populateLeaderboard();

    }

    @SuppressLint("NewApi")
    public void populateLeaderboard() {
        LeaderboardPlayer[] leaderboard;
        try {
            leaderboard = client.getLeaderboard();
            for (LeaderboardPlayer player : leaderboard) {
                TextView text = new TextView(this);
                text.setText(player.toString());
                view.addView(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}