package com.github.afikrim.flop.users;

import java.util.List;

public interface UserService {

    public List<User> getAll();
    public User store(UserRequest userRequest);
    public User getOne(Long id);
    public User updateOne(Long id, UserRequest userRequest);
    public User destroyOne(Long id);

}
