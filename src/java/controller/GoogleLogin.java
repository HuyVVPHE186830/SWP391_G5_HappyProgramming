package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import constant.IConstant;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GoogleAccount;
import model.User;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import service.FileConverter;
import service.ImageConverter;

@MultipartConfig
public class GoogleLogin extends HttpServlet {

    public static String getToken(String code) throws ClientProtocolException, IOException {
       
        String response = Request.Post(IConstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", IConstant.GOOGLE_CLIENT_ID)
                                .add("client_secret", IConstant.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", IConstant.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", IConstant.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);

        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");

        return accessToken;

    }

    public static GoogleAccount getUserInfo(final String accessToken) throws ClientProtocolException, IOException {

        String link = IConstant.GOOGLE_LINK_GET_USER_INFO + accessToken;

        String response = Request.Get(link).execute().returnContent().asString();

        GoogleAccount googlePojo = new Gson().fromJson(response, GoogleAccount.class);

        return googlePojo;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = UUID.randomUUID().toString();
        String dobString = request.getParameter("dob");
        Date dob = null;

        if (dobString != null && !dobString.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dob = sdf.parse(dobString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        Path avatarPath = Paths.get(getServletContext().getRealPath("/img/bc439871417621836a0eeea768d60944.jpg"));
        byte[] avatarBytes = Files.readAllBytes(avatarPath);
        String avatar = ImageConverter.gI().encode(avatarBytes);

        String cvBase64 = null;

        if ("mentor".equals(request.getParameter("role"))) {
            Part cvPart = request.getPart("cv");
            if (cvPart != null && cvPart.getSize() > 0) {
                FileConverter fileConverter = FileConverter.getInstance();
                String cvPrefix = "cvFile";
                File tempFile = File.createTempFile(cvPrefix, ".pdf");
                cvPart.write(tempFile.getAbsolutePath());
                cvBase64 = fileConverter.encode(tempFile);
                tempFile.delete();
            }
        }

        UserDAO dao = new UserDAO();
        List<User> users = dao.getAll();
        String redString = "";

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                redString += "*Username has been used<br>";
            }
        }
        if (!redString.isEmpty()) {
            session.setAttribute("error", redString);
            response.sendRedirect("googlelogin.jsp");

        } else {
            Date doc = new Date();
            int roleId = "mentor".equals(role) ? 2 : 3;
            User user = new User(username, password, firstName, lastName, dob, email, doc, avatar, cvBase64, true, true, roleId);
            dao.registerUser(user);
            session.setAttribute("user", user);
            response.sendRedirect("home");
        }
    }
}
