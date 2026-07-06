package com.example.fileservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private Date birthDate;
    private double salary;
}