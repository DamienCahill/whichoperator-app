package ie.whichoperator.whichoperator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LeaderBoardClient {
    private String URL;
    private String password;
    private String userName;
    public LeaderBoardClient(String userName, String password, String url) {
        this.userName = userName;
        this.password = password;
        this.URL = url;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean submitScore(String name, int score) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(URL + "/addScore?" + "name=" + name + "&score=" + (score)).openConnection();
        connection.setRequestMethod("POST");
        String encoded = Base64.getEncoder().encodeToString((this.userName+":"+this.password).getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic "+encoded);
        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            return true;
        }
        return false;
    }
}
