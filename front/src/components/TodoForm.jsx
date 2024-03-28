import React, { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { todoService } from "../services/todo-service";

const TodoForm = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const id = searchParams.get("id");
  const [isEditing, setIsEditing] = useState(false);

  const [todoData, setTodoData] = useState({
    name: "",
    description: "",
    state: "",
  });

  useEffect(() => {
    setIsEditing(id !== null);
    if (isEditing) {
      todoService
        .getTodoById(id)
        .then((response) => {
          const { name, description, state } = response.data;
          setTodoData({ name, description, state });
        })
        .catch((error) => {
          console.error("Error fetching todo details:", error);
        });
    }
  }, [id, isEditing]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTodoData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const { name, description, state } = todoData;
      if (isEditing) {
        await todoService.update({ id, name, description, state });
      } else {
        await todoService.create({ name, description, state });
      }
      navigate("/todos");
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="container mt-5">
      <h2>{isEditing ? "Modifier la tâche" : "Ajouter une tâche"}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Nom
          </label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={todoData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">
            Description
          </label>
          <input
            type="text"
            className="form-control"
            id="description"
            name="description"
            value={todoData.description}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="state" className="form-label">
            Etat
          </label>
          <br />
          <select
            name="state"
            id="state"
            className="form-select w-100 h-100"
            onChange={handleChange}
            value={todoData.state}
          >
            <option value="IN_PROGRESS">En cours</option>
            <option value="COMPLETED">Fini</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">
          {isEditing ? "Modifier" : "Ajouter"}
        </button>
      </form>
    </div>
  );
};

export default TodoForm;
