package com.assignment.musiclibrary.controller;

import com.assignment.musiclibrary.model.Track;
import com.assignment.musiclibrary.service.TrackService;
import com.assignment.musiclibrary.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add-track")
    public ResponseEntity<Object> addTrack(@RequestBody Track track, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return trackService.addTrack(track);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTrack(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return trackService.getTrack(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTracks(@RequestParam(required = false) Integer limit,
                                               @RequestParam(required = false) Integer offset,
                                               @RequestParam(required = false) String artistId,
                                               @RequestParam(required = false) String albumId,
                                               @RequestParam(required = false) Boolean hidden,
                                               @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return trackService.getAllTracks(limit, offset, artistId, albumId, hidden);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTrack(@PathVariable UUID id, @RequestBody Track track, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return trackService.updateTrack(id, track);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrack(@PathVariable UUID id, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return trackService.deleteTrack(id);
    }
}