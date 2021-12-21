package com.kalashnyk.santa.model;

import lombok.Data;

@Data
public class PlayerRequest {
    private String firstName;
    private String lastName;
    private String email;
}
