package com.hd.muzik.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private int id;
    private String name;
    private String songUrl;
    private int artist;
    private int album;
    private List<Genre> genres;
    private int likeCount;
    private String artistName;
}

