import api from "./api";
import { authHeader } from "../helpers/auth-header";

const getAll = () => {
  return api.get("/todos", { headers: authHeader() });
};

const create = (value) => {
  return api.post("/todos/admin/create", value, { headers: authHeader() });
};

const deleteTodo = (id) => {
  return api.delete(`/todos/admin/delete/${id}`, { headers: authHeader() });
};

const update = (value) => {
  return api.put("/todos/admin/update", value, { headers: authHeader() });
};

const getTodoById = (value) => {
  return api.get(`/todos/${value}`, { headers: authHeader() });
};

export const todoService = { getAll, create, deleteTodo, update, getTodoById };
