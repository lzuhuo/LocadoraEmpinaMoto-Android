package com.example.locadoraempinamoto.model;

public class AnimalNames {
    private String name;
    public AnimalNames(String animalName){
        name = animalName;
    }

    @Override
    public String toString() {
        return name;
    }
}
