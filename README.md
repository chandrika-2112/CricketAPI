# CricketAPI


## Overview

This project fetches cricket match data from the CricAPI and performs computations based on user input. It allows users to filter matches by team names and outputs details such as highest scores, team names, scores, images, and more.

## API URL

- **Base URL**: `https://api.cricapi.com/v1/currentMatches`
- **API Key**: `API_KEY`

## Key Features

1. **Fetch and Parse Data**: The project fetches JSON data from the CricAPI, which includes details about current cricket matches.

2. **Dynamic Filtering**: Users can input team names to filter matches and view details only for the specified teams.

3. **Match Details**: For each relevant match, the following details are displayed:
   - Match ID
   - Date & Time GMT
   - Match Status
   - Match Type
   - Team 1 Name
   - Team 1 Score
   - Team 1 Image
   - Team 2 Name
   - Team 2 Score
   - Team 2 Image

4. **Computations**:
   - **Highest Score**: Returns the highest score in one innings and the corresponding team name.
   - **Total Score Analysis**: Returns the number of matches where the combined total score of both teams exceeds 300.

## Setup

1. **Dependencies**:
   - Ensure you have Java Development Kit (JDK) installed (version 17 or higher recommended).
   - Use Maven for dependency management. Ensure you have Maven installed.

2. **Build and Run**:
   - Navigate to your project directory.
   - Use Maven to build and run the project:
     ```bash
     mvn clean install
     mvn exec:java -Dexec.mainClass="com.mycompany.mavenproject1.CricketProject.Cricket_Project"
     ```

## Key Points in Code

1. **Fetching Data**:
   - Uses `HttpURLConnection` to make GET requests to the CricAPI.
   - Reads and parses the JSON response using `BufferedReader` and `StringBuilder`.

2. **Processing Data**:
   - Uses `org.json` library to handle JSON data and extract relevant information.
   - Utilizes `Gson` for pretty-printing JSON data.

3. **User Input**:
   - Takes user input for team names using `Scanner`.
   - Filters and processes data based on the provided team names.

4. **Computations**:
   - Computes the highest score in one innings.
   - Counts the number of matches where the combined score of both teams exceeds 300.

5. **Output**:
   - Prints match details and results in a formatted manner.
   - Outputs the highest score and number of matches with a combined score over 300.

## Example Usage

The program will output the match details for the specified teams and provide the highest score and number of matches with a combined score over 300.

