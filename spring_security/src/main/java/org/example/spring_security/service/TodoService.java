package org.example.spring_security.service;

import org.example.spring_security.entity.Todo;
import org.example.spring_security.entity.User;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    public Todo save(Todo todo);

    public List<Todo> getAllTodo();


    public Optional<Todo> getTodoById(long id);

    public Todo updateTodo(Todo todo);

    public void removeTodo(long id);

    public List<Todo> getAllTodoByUser(String mail);
}
