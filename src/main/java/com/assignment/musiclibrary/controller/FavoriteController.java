package com.assignment.musiclibrary.controller;

import com.assignment.musiclibrary.model.Favorite;
import com.assignment.musiclibrary.service.FavoriteService;
import com.assignment.musiclibrary.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{category}")
    public ResponseEntity<Object> getFavorites(@PathVariable String category, @RequestParam(required = false) Integer limit,
                                               @RequestParam(required = false) Integer offset, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return favoriteService.getFavorites(category, limit, offset);
    }

    @PostMapping("/add-favorite")
    public ResponseEntity<Object> addFavorite(@RequestBody Favorite favorite, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return favoriteService.addFavorite(favorite);
    }

    @DeleteMapping("/remove-favorite/{id}")
    public ResponseEntity<Object> removeFavorite(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return favoriteService.removeFavorite(id);
    }
}