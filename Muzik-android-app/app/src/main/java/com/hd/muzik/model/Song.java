package com.hd.muzik.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song implements Serializable {
    private int id;
    private String name;
    private String songUrl;
    private int artist;
    private int album;
    private List<Genre> genres;
    private int likeCount;
    private String artistName;

    private List<String> getGenreNames(List<Genre> genres) {
        List<String> genreNames = new ArrayList<>();
        for (Genre genre : genres) {
            genreNames.add(genre.getName());
        }
        return genreNames;
    }
}

