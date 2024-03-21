package org.example.spring_security.service;

import org.example.spring_security.entity.Todo;
import org.example.spring_security.entity.User;
import org.example.spring_security.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository repository;
    @Override
    public Todo save(Todo todo) {
        repository.save(todo);
        return todo;
    }

    @Override
    public List<Todo> getAllTodo() {
        return repository.findAll();
    }


    @Override
    public Optional<Todo> getTodoById(long id) {
        return repository.findById(id);
    }

    @Override
    public Todo updateTodo(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public void removeTodo(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Todo> getAllTodoByUser(String mail){
        return repository.findAllByUser_Mail(mail);
    }
}
