package edu.icet.solorent.service;


import edu.icet.solorent.entity.User;

import java.util.List;

public interface UserService {
    void add(User user);

    void delete(Long id);

    void update(User user);

    User searchById(Long id);

    List<User> getAll();
}
