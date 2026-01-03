package com.quest.etna.service;

import com.quest.etna.model.User;

import java.util.List;

public interface ModelService<T> {


    public List<User> getList(Integer page , Integer limit);
    public T getOneByName(String name);
    public T createEntity(T entity);
    public T update(String username, T entity);
}
