package com.hd.muzik.model;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album implements Serializable {
    private int id;
    private String name;
    private String url;
    private List<Song> songs;
    private int countSong;
    private String artistName;
}
