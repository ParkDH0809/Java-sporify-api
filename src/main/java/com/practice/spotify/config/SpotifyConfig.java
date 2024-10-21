package com.practice.spotify.config;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyApi.Builder;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Component
public class SpotifyConfig {

    private static SpotifyApi spotifyApi;

    SpotifyConfig(@Value("${spotify.client-id}") String clientId, @Value("${spotify.client-secret}") String clientSecret) {
        spotifyApi = new Builder().setClientId(clientId).setClientSecret(clientSecret).build();;
    }

    public static String accessToken() {
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return "error";
        }
    }

}
