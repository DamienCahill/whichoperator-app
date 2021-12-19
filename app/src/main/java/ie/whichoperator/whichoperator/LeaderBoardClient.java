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
import android.util.Base64;
import java.util.Scanner;

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
        String encoded = Base64.encodeToString( (this.userName+ ":"+ this.password).getBytes(), Base64.DEFAULT);
        connection.setRequestProperty("Authorization", "Basic "+encoded);
        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LeaderboardPlayer[] getLeaderboard() throws IOException {
        //Create connection to the api
        HttpURLConnection connection = (HttpURLConnection) new java.net.URL(URL + "/topTen").openConnection();

        // set request to get
        connection.setRequestMethod("GET");
        String encoded = Base64.encodeToString( (this.userName+ ":"+ this.password).getBytes(), Base64.DEFAULT);
        connection.setRequestProperty("Authorization", "Basic "+encoded);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                response += scanner.nextLine();
                response += "\n";
            }
            scanner.close();
            response =response.replace("(", "");
            response=response.replace("[", "");
            response=response.replace("]", "");
            response=response.replace(")", "");
            String responseArray[] = null;
            responseArray = response.split(",");
            for (int i=0;i<responseArray.length;i++) {
                responseArray[i] = responseArray[i].trim();
            }
            LeaderboardPlayer[] leaderboard = new LeaderboardPlayer[responseArray.length/2];
            for (int i=0;i<responseArray.length;i+=2) {
                leaderboard[i/2] = new LeaderboardPlayer(responseArray[i].replace("'",""), Integer.parseInt(responseArray[i+1]));
            }
            return leaderboard;
        }
        // an error happened
        return null;
    }
}
