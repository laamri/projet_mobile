package com.example.lab7_galeriedestars.service;

import com.example.lab7_galeriedestars.dao.IDao;
import com.example.lab7_galeriedestars.model.Star;
import java.util.ArrayList;
import java.util.List;

public class StarService implements IDao<Star> {

    private List<Star> stars = new ArrayList<>();

    public StarService() {
        stars.add(new Star(1, "Emma Watson",
                "https://i.pravatar.cc/150?img=1", 4.5f));

        stars.add(new Star(2, "Scarlett Johansson",
                "https://i.pravatar.cc/150?img=5", 4.0f));

        stars.add(new Star(3, "Angelina Jolie",
                "https://i.pravatar.cc/150?img=9", 5.0f));

        stars.add(new Star(4, "George Clooney",
                "https://i.pravatar.cc/150?img=12", 3.5f));

        stars.add(new Star(5, "Leonardo DiCaprio",
                "https://i.pravatar.cc/150?img=15", 4.0f));

        stars.add(new Star(6, "Margot Robbie",
                "https://i.pravatar.cc/150?img=20", 4.5f));

        stars.add(new Star(7, "Brad Pitt",
                "https://i.pravatar.cc/150?img=33", 3.5f));

        stars.add(new Star(8, "Meryl Streep",
                "https://i.pravatar.cc/150?img=47", 5.0f));

        stars.add(new Star(9, "Tom Hanks",
                "https://i.pravatar.cc/150?img=51", 4.0f));

        stars.add(new Star(10, "Michelle Rodriguez",
                "https://i.pravatar.cc/150?img=60", 5.0f));
    }

    @Override
    public boolean create(Star o) { return stars.add(o); }

    @Override
    public boolean update(Star o) {
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).getId() == o.getId()) {
                stars.set(i, o);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Star o) { return stars.remove(o); }

    @Override
    public Star findById(int id) {
        for (Star s : stars)
            if (s.getId() == id) return s;
        return null;
    }

    @Override
    public List<Star> findAll() { return stars; }
}