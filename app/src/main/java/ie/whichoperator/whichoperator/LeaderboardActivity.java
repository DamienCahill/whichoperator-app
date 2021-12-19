package ie.whichoperator.whichoperator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;


public class LeaderboardActivity extends AppCompatActivity {
    LeaderBoardClient client;
    LinearLayout TESTview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        client = new LeaderBoardClient(BuildConfig.USERNAME, BuildConfig.PASSWORD, BuildConfig.URL);
        TESTview = (LinearLayout) findViewById(R.id.linear);
        if (TESTview !=null)
            populateLeaderboard();
        else
            System.out.println("NULL VIEW!!!!");
    }

    @SuppressLint("NewApi")
    public void populateLeaderboard() {
        LeaderboardPlayer[] leaderboard;
        try {
            leaderboard = client.getLeaderboard();
            for (LeaderboardPlayer player : leaderboard) {
                TextView text = new TextView(this);
                text.setText(player.toString());
                TESTview.addView(text);
            }
            if (leaderboard != null) {
                System.out.println(leaderboard[0].toString());
            } else {
                System.out.println("null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}