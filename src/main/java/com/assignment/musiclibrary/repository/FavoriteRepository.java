package com.assignment.musiclibrary.repository;

import com.assignment.musiclibrary.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
