package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import constant.AIConstant; // Import the AIConstant class

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatBotServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user message from the request
        String userMessage = request.getParameter("message");

        // Call OpenAI API and get the chatbot response
        String botResponse = callOpenAIAPI(userMessage);

        // Set the response back to the JSP page
        request.setAttribute("userMessage", userMessage);
        request.setAttribute("botResponse", botResponse);
        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    private String callOpenAIAPI(String userMessage) throws IOException {
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = null;

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + AIConstant.OPENAI_API_KEY); // Use the constant
        connection.setDoOutput(true);

        // Prepare the request body with the user message
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4o-mini");
        JsonArray messages = new JsonArray();
        JsonObject userMessageObj = new JsonObject();
        userMessageObj.addProperty("role", "user");
        userMessageObj.addProperty("content", userMessage);
        messages.add(userMessageObj);
        requestBody.add("messages", messages);

        // Write the request body
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(requestBody.toString());
        writer.flush();
        writer.close();

        // Get response code
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Success: Read the API response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the response to get the chatbot's reply
            JsonParser parser = new JsonParser();
            JsonElement responseElement = parser.parse(response.toString());
            JsonObject responseObject = responseElement.getAsJsonObject();
            return responseObject.getAsJsonArray("choices").get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
        } else if (responseCode == 403) {
            throw new IOException("Forbidden: Check your API key and model access.");
        } else if (responseCode == 429) {
            throw new IOException("Rate limit exceeded. Try again later.");
        } else {
            throw new IOException("Error: Received HTTP response code " + responseCode);
        }
    }
}
