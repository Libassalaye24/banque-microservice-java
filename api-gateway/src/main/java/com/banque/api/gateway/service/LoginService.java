package com.banque.api.gateway.service;



import com.banque.api.gateway.model.LoginRequest;
import com.banque.api.gateway.model.LoginResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

    @Value("${app.keycloak.login.url}")
    private String loginUrl;
    @Value("${app.keycloak.client-secret}")
    private String clientSecret;
    @Value("${app.keycloak.grant-type}")
    private String grantType;
    @Value("${app.keycloak.client-id}")
    private String clientId;
    @Value("${app.keycloak.realm.name}")
    private String realm;
    @Value("${app.keycloak.server.url}")
    private String serverUrl;


    private final RestTemplate restTemplate;

    public LoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<LoginResponse> login (LoginRequest request)  {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", request.getUsername());
        map.add("password", request.getPassword());
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(loginUrl, httpEntity, LoginResponse.class);

        return ResponseEntity.status(200).body(loginResponse.getBody());

    }


//     private void addUserToKeycloak(ClientDto clientDto) {
//     try {
//         Keycloak keycloak = KeycloakBuilder.builder()
//                 .serverUrl(serverUrl)
//                 .realm(realm)
//                 .clientId(clientId)
//                 .clientSecret(clientSecret)
//                 .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
//                 .build();

//         // Create a new user representation based on the ClientDto
//         org.keycloak.representations.idm.UserRepresentation user = new UserRepresentation();
//         user.setUsername(clientDto.getUsername());
//         // Set other user attributes from the ClientDto

//         // Define the user resource and send a request to Keycloak to create the user
//         UsersResource usersResource = keycloak.realm(realm).users();
//         Response response = usersResource.create(user);

//         // Check if the user creation was successful
//         if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
//             // Handle error case
//         }

//         // Close the Keycloak client
//         keycloak.close();
//     } catch (Exception e) {
//         // Handle Keycloak API call exception
//     }
// }
}
