package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.VkRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@WebServlet("/bot")
public class BotServlet extends HttpServlet {

    private final static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private final String VK_API = "https://api.vk.com/method/";
    private final String VERSION = "5.199";
    private final String confirmationToken = "f3e90c2e";
    private final String TOKEN = "vk1.a.th-PD-tdTOkhpfiOh-yBZmymG10mu3E6Vz9ccmHYgoWXZxCau5qzK6sMfEg0gGHc0UmLOczBAnJn10mPjh6y9szTUxrmhnQlcHahElGhvQxfjbIGG4OwLuDH4PKZhWri9dQaHJjk9Cdedh1kFAbtoF4X3VGp599xa6vMaeFVHOAvd25mZx2bIZ4I43lTkdzPviA9zQEDiUkQvLqTTzBOvw";
    private final String COMMON_TEXT = "Вы написали: ";
    private final String ERROR_TEXT = "Поддерживается только текстовый формат";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VkRequest vkBody = GSON.fromJson(
            getBody(request), VkRequest.class
        );
        switch (vkBody.getType()) {
            case "confirmation" -> {
                response.setContentType("text/plain");
                response.getWriter().write(confirmationToken);
            }
            case "message_new" -> {
                if (vkBody.getObject() != null) {
                    try {
                        sendMessage(vkBody);
                    } catch (Exception e) {
                        throw new RuntimeException("send message", e);
                    }
                }
                response.setContentType("text/plain");
                response.getWriter().write("ok");
            }
        }
    }

    private String getBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private void sendMessage(VkRequest vkBody) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(
                new URI(VK_API + "messages.send" + getParams(vkBody))
            )
            .headers("Content-Type", "application/x-www-form-encoded")
            .POST(HttpRequest.BodyPublishers.ofString("mock"))
            .build();
        sendRequest(request);
    }

    private String getParams(VkRequest vkBody) {
        String message = vkBody.getObject().getMessage().getText();
        return "?access_token=" +
            TOKEN +
            "&random_id=0&v=" +
            VERSION +
            "&user_id=" +
            vkBody.getObject().getMessage().getFromId() +
            "&message=" +
            encode(
                (message.isBlank() ? ERROR_TEXT : COMMON_TEXT + message)
            );
    }

    public String encode(String parameter) {
        return URLEncoder.encode(parameter, StandardCharsets.UTF_8);
    }

    private void sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
