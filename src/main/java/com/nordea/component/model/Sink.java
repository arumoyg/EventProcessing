package com.nordea.component.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sink {

    private long id;
    private byte[] message;
    private byte[] salt;
    private byte[] hash;
}
