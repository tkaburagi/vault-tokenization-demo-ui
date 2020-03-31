package run.kabuctl.vaulttokenizationdemoui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class UiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UiService(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        this.restTemplate = builder.build();
        this.objectMapper = objectMapper;
    }

    public User[] getAllUsers() throws Exception {
        String url = "http://127.0.0.1:8080";
        String result = this.restTemplate.getForObject(url + "/api/v1/get-all-users", String.class);

        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }

    public User[] getTransformedUsers() throws Exception {
        String url = "http://127.0.0.1:8080";
        String result = this.restTemplate.getForObject(url + "/api/v1/get-transformed-users", String.class);

        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }

    public User[] getEncryptedUsers() throws Exception {
        String url = "http://127.0.0.1:8080";
        String result = this.restTemplate.getForObject(url + "/api/v1/get-encrypted-users", String.class);

        User[] userList = this.objectMapper.readValue(result, User[].class);
        return userList;
    }

    public void addOneEncryptedUser(String username, String password, String email, String creditcard, String howto) {
        String targetUrl = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> input = new LinkedHashMap<>();
        input.put("username", username);
        input.put("password", password);
        input.put("email", email);
        input.put("creditcard", creditcard);

        System.out.println("howto:" + howto);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(input, headers);

        if (howto.equals("transit")) {
            targetUrl = UriComponentsBuilder.fromUriString("http://127.0.0.1:8080")
                    .path("api/v1/encrypt/add-user")
                    .queryParam("username", username)
                    .queryParam("password", password)
                    .queryParam("email", email)
                    .queryParam("creditcard", creditcard)
                    .build()
                    .toString();
        } else if (howto.equals("transformation")) {
            targetUrl = UriComponentsBuilder.fromUriString("http://127.0.0.1:8080")
                    .path("api/v1/transform/add-user")
                    .queryParam("username", username)
                    .queryParam("password", password)
                    .queryParam("email", email)
                    .queryParam("creditcard", creditcard)
                    .build()
                    .toString();
        } else if (howto.equals("simple-transformation")) {
            targetUrl = UriComponentsBuilder.fromUriString("http://127.0.0.1:8080")
                    .path("api/v1/simple-transform/add-user")
                    .queryParam("username", username)
                    .queryParam("password", password)
                    .queryParam("email", email)
                    .queryParam("creditcard", creditcard)
                    .build()
                    .toString();
            System.out.println(targetUrl);
        }
        String obj = this.restTemplate.postForObject(targetUrl, entity, String.class);
    }
}