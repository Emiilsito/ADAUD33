package com.example;

public class Main {
    public static void main(String[] args) {
        SpaceService spaceService = new SpaceService();
        System.out.println(spaceService.getSpaceByCode("MR-001"));
    }
}
