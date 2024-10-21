package com.practice.spotify.controller;

import com.practice.spotify.dto.SearchResponseDto;
import com.practice.spotify.service.SpotifyService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<SearchResponseDto>> searchSpotifyMusic(@PathVariable String keyword) {
        return ResponseEntity.ok(spotifyService.search(keyword));
    }
}
