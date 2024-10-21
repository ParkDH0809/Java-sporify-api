package com.practice.spotify.service;

import com.practice.spotify.dto.SearchResponseDto;
import com.practice.spotify.config.SpotifyConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
public class SpotifyService {

    SpotifyApi spotifyApi = new SpotifyApi.Builder().setAccessToken(SpotifyConfig.accessToken()).build();

    public List<SearchResponseDto> search(String keyword) {
        List <SearchResponseDto> searchResponseDtoList = new ArrayList<>();

        try {
            SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(keyword)
                    .limit(10)
                    .build();

            Paging<Track> searchResult = searchTrackRequest.execute();
            Track[] tracks = searchResult.getItems();

            for (Track track : tracks) {
                String title = track.getName();

                AlbumSimplified album = track.getAlbum();
                ArtistSimplified[] artists = album.getArtists();
                String artistName = artists[0].getName();


                Image[] images = album.getImages();
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "";

                String albumName = album.getName();

                searchResponseDtoList.add(toSearchDto(artistName, title, albumName, imageUrl));
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
        }
        return searchResponseDtoList;
    }

    public SearchResponseDto toSearchDto(String artistName, String title, String albumName, String imageUrl) {
        return SearchResponseDto.builder()
                .artistName(artistName)
                .title(title)
                .albumName(albumName)
                .imageUrl(imageUrl)
                .build();
    }

}
