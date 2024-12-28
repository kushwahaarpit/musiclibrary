package com.assignment.musiclibrary.controller;

import com.assignment.musiclibrary.api.ApiResponse;
import com.assignment.musiclibrary.model.Artist;
import com.assignment.musiclibrary.service.ArtistService;
import com.assignment.musiclibrary.util.JwtUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService, JwtUtil jwtUtil) {
        this.artistService = artistService;
        this.jwtUtil = jwtUtil;
    }
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> getAllArtists(@RequestParam(required = false) Pageable limit,
                                           @RequestParam(required = false) Integer grammy,
                                           @RequestParam(required = false) Boolean hidden) {
        List<Artist> artists = artistService.getAllArtists(limit, grammy, hidden);
        return ResponseEntity.ok(new ApiResponse(200, "Artists retrieved successfully.", artists));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtist(@PathVariable UUID id) {
        Artist artist = artistService.getArtistById(id);
        if (artist != null) {
            return ResponseEntity.ok(new ApiResponse(200, "Artist retrieved successfully.", artist));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(404, "Artist not found.", null));
    }

    @PostMapping("/add-artist")
    public ResponseEntity<?> addArtist(@RequestBody Artist artist) {
        Artist createdArtist = artistService.addArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Artist created successfully.", createdArtist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArtist(@RequestHeader("Authorization") String token,
                                          @PathVariable UUID id,
                                          @RequestBody Artist artist) {
        if (!jwtUtil.isAdmin(token)) {
            return ResponseEntity.status(403).body("Forbidden Access");
        }

        Artist updatedArtist = artistService.updateArtist(id, artist);
        if (updatedArtist == null) {
            return ResponseEntity.status(404).body("Artist Not Found");
        }
        return ResponseEntity.status(204).build();
    }

    // DELETE /artists/{id} - Delete Artist
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@RequestHeader("Authorization") String token,
                                          @PathVariable UUID id) {
        if (!jwtUtil.isAdmin(token)) {
            return ResponseEntity.status(403).body("Forbidden Access");
        }

        boolean isDeleted = artistService.deleteArtist(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).body("Artist Not Found");
        }
        return ResponseEntity.ok("Artist deleted successfully.");
    }
}