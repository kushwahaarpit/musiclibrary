package com.assignment.musiclibrary.service;

import com.assignment.musiclibrary.model.Favorite;
import com.assignment.musiclibrary.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public ResponseEntity<Object> getFavorites(String category, Integer limit, Integer offset) {
        return ResponseEntity.status(200).body("Favorites fetched successfully.");
    }

    public ResponseEntity<Object> addFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
        return ResponseEntity.status(201).body("Favorite added successfully.");
    }

    public ResponseEntity<Object> removeFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Favorite not found");
        }

        favoriteRepository.deleteById(id);
        return ResponseEntity.status(200).body("Favorite removed successfully.");
    }
}