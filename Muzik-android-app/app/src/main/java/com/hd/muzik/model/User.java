package com.hd.muzik.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String fullName;
    private String username;
    private String createdAt;
    private String avatar;
    private String role;
}