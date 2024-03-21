package org.example.spring_security.controller;

import org.example.spring_security.entity.Todo;
import org.example.spring_security.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            String username = authentication.getName();
            List<Todo> userTodos = service.getAllTodoByUser(username);
            return ResponseEntity.status(200).body(userTodos);

        } else {
            List<Todo> allTodos = service.getAllTodo();
            return ResponseEntity.status(200).body(allTodos);
        }
    }


    @PostMapping("/todos/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(201).body(service.save(todo));
    }

    @DeleteMapping("/todos/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTodo(@PathVariable long id) {
        service.removeTodo(id);
        return ResponseEntity.status(200).body("");
    }

    @PutMapping("/todos/admin/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(200).body(service.updateTodo(todo));
    }

}
