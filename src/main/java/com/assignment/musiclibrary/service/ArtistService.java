package com.assignment.musiclibrary.service;

import com.assignment.musiclibrary.model.Artist;
import com.assignment.musiclibrary.repository.ArtistRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public List<Artist> getAllArtists(Pageable pageable, Integer grammy, Boolean hidden) {
        if (grammy != null && hidden != null) {
            return artistRepository.findByGrammyAndHidden(grammy, hidden, pageable);
        }
        return artistRepository.findAll(pageable).getContent();
    }

    public Artist getArtistById(UUID id) {
        return artistRepository.findById(id).orElse(null);
    }

    public Artist addArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist updateArtist(UUID id, Artist artist) {
        if (artistRepository.existsById(id)) {
            artist.setArtistId(id);
            return artistRepository.save(artist);
        }
        return null;
    }

    public boolean deleteArtist(UUID id) {
        if (artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
            return true;
        }
        return false;
    }
}