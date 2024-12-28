package com.assignment.musiclibrary.controller;

import com.assignment.musiclibrary.model.Album;
import com.assignment.musiclibrary.service.AlbumService;
import com.assignment.musiclibrary.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private JwtUtil jwtUtil;

    // GET /albums - Retrieve All Albums
    @GetMapping
    public ResponseEntity<?> getAllAlbums(@RequestHeader("Authorization") String token,
                                          @RequestParam(value = "limit", defaultValue = "5") int limit,
                                          @RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "hidden", required = false) Boolean hidden,
                                          @RequestParam(value = "artist_id", required = false) String artistId) {
        // Handle access control based on token if needed

        var albums = albumService.getAllAlbums(limit, offset, hidden, artistId);
        return ResponseEntity.ok(albums);
    }

    // GET /albums/{id} - Retrieve an Album
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbumById(@RequestHeader("Authorization") String token,
                                          @PathVariable Long id) {
        var album = albumService.getAlbumById(id);
        if (album == null) {
            return ResponseEntity.status(404).body("Resource Doesn't Exist");
        }
        return ResponseEntity.ok(album);
    }

    // POST /albums/add-album - Add New Album
    @PostMapping("/add-album")
    public ResponseEntity<?> addAlbum(@RequestHeader("Authorization") String token,
                                      @RequestBody Album album) {
        if (!jwtUtil.isAdmin(token)) {
            return ResponseEntity.status(403).body("Forbidden Access");
        }

        Album createdAlbum = albumService.addAlbum(album);
        return ResponseEntity.status(201).body("Album created successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAlbum(@PathVariable Long id, @RequestBody Album album, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return albumService.updateAlbum(id, album);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAlbum(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return albumService.deleteAlbum(id);
    }
}
