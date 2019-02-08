package com.lucy.javaspring.javafirebase.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by lucy on 8/02/19.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {

    @Id
    private String id;
    private String name;
    private String email;

}
