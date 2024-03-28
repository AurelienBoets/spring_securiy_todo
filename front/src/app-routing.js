import { createBrowserRouter, redirect } from "react-router-dom";
import { getUserDetails } from "./helpers/user-details";
import Login from "./components/Login";
import Header from "./shared/Header";
import Register from "./components/Register";
import TodoList from "./components/TodoList";
import TodoForm from "./components/TodoForm";

const userDetails = getUserDetails();

const isAdmin = () => {
  if (userDetails && userDetails.role === "ROLE_ADMIN") {
    return true;
  } else {
    if (isLoggedIn()) {
      return redirect("/todos");
    }
  }
};

const isLoggedIn = () => {
  const user = localStorage.getItem("user");
  if (!!user) {
    return true;
  } else {
    return redirect("/");
  }
};

const router = createBrowserRouter([
  {
    element: <Header />,
    children: [
      {
        path: "/",
        element: <Login />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/register",
        element: <Register />,
      },
      {
        path: "/todos",
        element: <TodoList />,
        loader: () => isLoggedIn(),
      },
      {
        path: "/todos/create",
        element: <TodoForm />,
        loader: () => isAdmin(),
      },
      {
        path: "/todos/update",
        element: <TodoForm />,
        loader: () => isAdmin(),
      },
    ],
  },
]);

export default router;
