package com.assignment.musiclibrary.repository;

import com.assignment.musiclibrary.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    List<Artist> findByGrammyAndHidden(Integer grammy, Boolean hidden, Pageable pageable);
}
