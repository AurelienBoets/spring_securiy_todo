import api from "./api";

const register = (name, email, password) => {
  return api.post("/auth/register", { name, email, password });
};

const login = (email, password) => {
  return api.post("/auth/login", { mail: email, password }).then((response) => {
    console.log(response.data);
    if (response.data.token) {
      localStorage.setItem("user", JSON.stringify({ data: response.data }));
    }
    return response.data;
  });
};

const logout = () => {
  localStorage.removeItem("user");
};

export default { register, login, logout };
