package com.assignment.musiclibrary.service;

import com.assignment.musiclibrary.model.Track;
import com.assignment.musiclibrary.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public ResponseEntity<Object> addTrack(Track track) {
        trackRepository.save(track);
        return ResponseEntity.status(201).body("Track created successfully");
    }

    public ResponseEntity<Object> getTrack(UUID id) {
        Track track = trackRepository.findById(id).orElse(null);
        if (track == null) {
            return ResponseEntity.status(404).body("Track not found");
        }

        return ResponseEntity.status(200).body(track);
    }

    public ResponseEntity<Object> getAllTracks(Integer limit, Integer offset, String artistId, String albumId, Boolean hidden) {
        return ResponseEntity.status(200).body("Tracks fetched successfully");
    }


    public ResponseEntity<Object> updateTrack(UUID id, Track track) {
        if (!trackRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Track not found");
        }

        Track existingTrack = trackRepository.findById(id).get();
        existingTrack.setName(track.getName());
        existingTrack.setDuration(track.getDuration());
        existingTrack.setHidden(track.isHidden());

        trackRepository.save(existingTrack);
        return ResponseEntity.status(204).body("Track updated successfully.");
    }

    public ResponseEntity<Object> deleteTrack(UUID id) {
        if (!trackRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Track not found");
        }

        trackRepository.deleteById(id);
        return ResponseEntity.status(200).body("Track deleted successfully.");
    }
}
