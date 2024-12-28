package com.assignment.musiclibrary.repository;

import com.assignment.musiclibrary.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {

}
