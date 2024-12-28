package com.assignment.musiclibrary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Favorite {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String category; // "artist", "album", "track"
    private Long itemId; // ID of the item (Artist, Album, Track)
    private String createdAt;


}