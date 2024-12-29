package com.assignment.musiclibrary.service;

import com.assignment.musiclibrary.model.Album;
import com.assignment.musiclibrary.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    // Method to get all albums with filters
    public List<Album> getAllAlbums(int limit, int offset, Boolean hidden, String artistId) {
        return albumRepository.findAll();
    }

    // Method to get album by ID
    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    // Method to add a new album
    public Album addAlbum(Album album) {
        return albumRepository.save(album);
    }

    public ResponseEntity<Object> updateAlbum(Long id, Album album) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Album not found");
        }

        Album existingAlbum = albumRepository.findById(id).get();
        existingAlbum.setName(album.getName());
        existingAlbum.setYear(album.getYear());
        existingAlbum.setHidden(album.isHidden());

        albumRepository.save(existingAlbum);
        return ResponseEntity.status(204).body("Album updated successfully");
    }

    public ResponseEntity<Object> deleteAlbum(Long id) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Album not found");
        }

        albumRepository.deleteById(id);
        return ResponseEntity.status(200).body("Album deleted successfully");
    }
}
