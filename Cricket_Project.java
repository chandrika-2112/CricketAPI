import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Cricket_Project {

    public static void main(String[] args) {
        String apiKey = "4de54089-c0f0-4482-9bee-c5b02e891891";
        String apiUrl = "https://api.cricapi.com/v1/currentMatches?apikey=" + apiKey + "&offset=0"; // Replace with the actual API URL

        // Take team names as input from the user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the team names you want to filter (comma-separated):");
        String input = scanner.nextLine();
        Set<String> teamNames = new HashSet<>();
        for (String name : input.split(",")) {
            teamNames.add(name.trim());
        }

        try {
            // Fetch data from the API
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", apiKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the JSON response
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            String prettyJsonString = gson.toJson(jsonElement);
            //System.out.println("JSON Response: " + prettyJsonString);

            // Parse the JSON data
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray matches = jsonResponse.getJSONArray("data");

            int highestScore = 0;
            String highestScoringTeam = "";
            int matchesWith300Plus = 0;

            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.getJSONObject(i);

                // Extract match details
                JSONArray teamInfoArray = match.getJSONArray("teamInfo");
                JSONArray scoreArray = match.getJSONArray("score");

                boolean matchIncludesFilteredTeams = false;
                int totalScore = 0;

                // Check if the match involves any of the specified teams
                for (int j = 0; j < teamInfoArray.length(); j++) {
                    String teamName = teamInfoArray.getJSONObject(j).getString("name");
                    if (teamNames.contains(teamName)) {
                        matchIncludesFilteredTeams = true;
                    }
                }

                // Process the match if it includes any of the filtered teams
                if (matchIncludesFilteredTeams) {
                    // Print match details
                    String matchId = match.getString("id");
                    String dateTimeGMT = match.getString("dateTimeGMT");
                    String matchStatus = match.getString("status");
                    String matchType = match.getString("matchType");

                    JSONObject team1Info = teamInfoArray.getJSONObject(0);
                    JSONObject team2Info = teamInfoArray.getJSONObject(1);

                    String team1Name = team1Info.getString("name");
                    String team1Image = team1Info.getString("img");
                    int team1Score = getScoreByTeamName(scoreArray, team1Name);

                    String team2Name = team2Info.getString("name");
                    String team2Image = team2Info.getString("img");
                    int team2Score = getScoreByTeamName(scoreArray, team2Name);

                    System.out.println("Match ID: " + matchId);
                    System.out.println("Date & Time GMT: " + dateTimeGMT);
                    System.out.println("Match Status: " + matchStatus);
                    System.out.println("Match Type: " + matchType);
                    System.out.println("Team 1 Name: " + team1Name);
                    System.out.println("Team 1 Score: " + team1Score);
                    System.out.println("Team 1 Image: " + team1Image);
                    System.out.println("Team 2 Name: " + team2Name);
                    System.out.println("Team 2 Score: " + team2Score);
                    System.out.println("Team 2 Image: " + team2Image);
                    System.out.println();

                    // Compute highest score
                    if (team1Score > highestScore) {
                        highestScore = team1Score;
                        highestScoringTeam = team1Name;
                    }
                    if (team2Score > highestScore) {
                        highestScore = team2Score;
                        highestScoringTeam = team2Name;
                    }

                    // Compute number of matches with total 300+ score
                    totalScore = team1Score + team2Score;
                    if (totalScore > 300) {
                        matchesWith300Plus++;
                    }
                }
            }

            // Print results
            System.out.println("Highest Score: " + highestScore + " and Team Name is: " + highestScoringTeam);
            System.out.println("Number Of Matches with total 300 Plus Score: " + matchesWith300Plus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getScoreByTeamName(JSONArray scoreArray, String teamName) {
        for (int i = 0; i < scoreArray.length(); i++) {
            JSONObject score = scoreArray.getJSONObject(i);
            if (score.getString("inning").contains(teamName)) {
                return score.getInt("r");
            }
        }
        return 0;
    }
}
