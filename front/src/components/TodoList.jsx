import React, { useEffect, useState } from "react";
import { getUserDetails } from "../helpers/user-details";
import { useNavigate } from "react-router-dom";
import { todoService } from "../services/todo-service";
import { stateEnum } from "../helpers/state-enum";

function TodoList() {
  const [todos, setTodos] = useState([]);
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const userDetails = getUserDetails();
  const isAdmin = () => {
    return userDetails && userDetails.role === "ROLE_ADMIN";
  };

  useEffect(() => {
    todoService
      .getAll()
      .then((response) => {
        setTodos(response.data);
      })
      .catch((error) => {
        setError("Erreur lors de la récupération des taches.");
      });
  }, []);

  const handleDeleteProduct = (todoId) => {
    todoService
      .deleteTodo(todoId)
      .then(() => {
        setTodos(todos.filter((todo) => todo.id !== todoId));
      })
      .catch((error) => {
        setError("Erreur lors de la suppression de la tache.");
      });
  };

  return (
    <div className="container mt-5">
      <h2>Todo List</h2>
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
      <table className="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Etat</th>
            {isAdmin() && <th></th>}
          </tr>
        </thead>
        <tbody>
          {todos.map((todo, index) => (
            <tr key={todo.id}>
              <th scope="row">{index + 1}</th>
              <td>{todo.name}</td>
              <td>{todo.description}</td>
              <td>{stateEnum(todo.state)}</td>
              {isAdmin() && (
                <>
                  <td>
                    <span
                      className="btn btn-danger"
                      onClick={() => handleDeleteProduct(todo.id)}
                    >
                      Supprimer
                    </span>
                    <span
                      className="btn btn-warning ml-2"
                      onClick={() => navigate(`/todos/update?id=${todo.id}`)}
                    >
                      Modifier
                    </span>
                  </td>
                </>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TodoList;
