package com.example.Trailers.integration.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GenreCache {

    private final Map<Integer, String> genres = new HashMap<>();

    public void put(Integer id, String name) {
        genres.put(id, name);
    }

    public String getName(Integer id) {
        return genres.getOrDefault(id, "Unknown");
    }

    public boolean isEmpty() {
        return genres.isEmpty();
    }
}
