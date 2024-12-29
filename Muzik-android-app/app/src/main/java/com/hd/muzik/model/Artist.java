package com.hd.muzik.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Artist implements Serializable {
    private int id;
    private String name;
    private String photoUrl;
    private List<Integer> albums;
    private List<Integer> songs;
}
