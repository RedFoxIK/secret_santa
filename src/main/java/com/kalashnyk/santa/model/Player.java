package com.kalashnyk.santa.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Player {
    private final String firstName;
    private final String lastName;
    private final String email;
}
