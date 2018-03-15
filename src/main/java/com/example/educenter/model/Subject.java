package com.example.educenter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subject")
@Entity

public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private String lecturer;
    @ManyToMany(mappedBy = "subjects")
    private List<User> users;
}
