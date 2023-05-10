import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import SignUp from "./components/SignUp";
import SignIn from "./components/SignIn";
import ProjectDirectory from "./components/project/ProjectDirectory";
import DataTask from "./components/dataTask/DataTask";
import TagScreen from "./components/tags/TagsScreen";
import ProjectManagement from "./components/projectManagement/ProjectManagement";
import AnnotationScreen from "./components/annotation/AnnotationScreen";
import ImportScreen from "./components/import/ImportScreen";
import Export from "./components/export/Export";
import ErrorPage from "./components/ErrorPage";
import ChartScreen from "./components/charts/ChartScreen";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<SignIn />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        {localStorage.getItem("auth_token") ? (
          <>
            <Route path="/projects" element={<ProjectDirectory />} />
            <Route path="/projects/:projectId/tasks" element={<DataTask />} />
            <Route
              path="/projects/:projectId/users"
              element={<ProjectManagement />}
            />
            <Route path="/projects/:projectId/tags" element={<TagScreen />} />
            <Route
              path="/projects/:projectId/imports"
              element={<ImportScreen />}
            />
            <Route
              path="/projects/:projectId/tasks/:taskId/annotation"
              element={<AnnotationScreen />}
            />{" "}
            <Route
              path="/projects/:projectId/imports"
              element={<ImportScreen />}
            />
            <Route path="/projects/:projectId/exports" element={<Export />} />
            <Route path="/projects/:projectId/tags" element={<TagScreen />} />
            <Route
              path="/projects/:projectId/charts"
              element={<ChartScreen />}
            />
          </>
        ) : (
          <Route path="/signup" element={<SignUp />} />
        )}
        <Route path="*" element={<ErrorPage />} />
      </Routes>
      <ToastContainer />
    </BrowserRouter>
  );
}

export default App;
