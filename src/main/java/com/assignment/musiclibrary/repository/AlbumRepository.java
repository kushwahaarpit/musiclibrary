package com.assignment.musiclibrary.repository;


import com.assignment.musiclibrary.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
