import React, { useEffect, useState } from "react";
import { Box, Typography, Link } from "@mui/material";

import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import axios from "axios";
import {
  useParams,
  useLocation,
  useNavigate,
  Link as Linked,
} from "react-router-dom";

import DeleteTask from "./DeleteTask";
import { BASEURL, PROJECTS, TASKS } from "../Url";
import Header from "../Header";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function DataTask() {
  let { projectId } = useParams();
  const location = useLocation();
  const projectUrl = `${BASEURL + PROJECTS}/${projectId + TASKS}`;
  const [allDataTask, setAllDataTask] = useState([]);

  const getAllDataTask = async () => {
    await axios({
      method: "get",
      url: projectUrl,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        setAllDataTask(response.data);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const columns = [
    { id: "taskId", label: "ID", minWidth: 170, align: "center" },
    {
      id: "taskContent",
      label: "DATA",
      minWidth: 100,
      align: "center",
    },
    {
      id: "option",
      label: "OPTION",
      minWidth: 170,
      align: "center",
    },
  ];

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };
  const navigate = useNavigate();

  const handleNavigate = (id, data, selectedTag, selectedTagId) => {
    navigate(`${location.pathname}/${id}/annotation`, {
      state: {
        id,
        data,
        selectedTag,
        selectedTagId,
      },
    });
  };

  const handleChangeRowsPerPage = (event) => {
    event.preventDefault();
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  useEffect(() => {
    getAllDataTask();
  }, []);

  return (
    <>
      <Header />
      <Box
        className="title"
        sx={{
          padding: "1rem",
          height: "10vh",
          width: "80%",
          margin: "auto",
        }}
      >
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <h2>Projects Task</h2>
          {localStorage.getItem("role") === "manager" ? (
            <Box sx={{ display: "flex", gap: "2rem" }}>
              <Linked component="button" to={`/projects/${projectId}/users`}>
                <p style={{ fontSize: "1.4rem" }}>User</p>
              </Linked>
              <Linked component="button" to={`/projects/${projectId}/tags`}>
                <p style={{ fontSize: "1.4rem" }}>Tags</p>
              </Linked>
              <Linked component="button" to={`/projects/${projectId}/imports`}>
                <p style={{ fontSize: "1.4rem" }}>Imports</p>
              </Linked>
              <Linked component="button" to={`/projects/${projectId}/exports`}>
                <p style={{ fontSize: "1.4rem" }}>Exports</p>
              </Linked>
              <Linked component="button" to={`/projects/${projectId}/charts`}>
                <p style={{ fontSize: "1.4rem" }}>Charts</p>
              </Linked>
            </Box>
          ) : (
            <Box></Box>
          )}
        </Box>
      </Box>
      <Box>
        {allDataTask ? (
          <Box
            className="table"
            sx={{ display: "flex", justifyContent: "center" }}
          >
            <Paper sx={{ width: "90%" }}>
              <TableContainer sx={{ maxHeight: 600 }}>
                <Table stickyHeader aria-label="sticky table">
                  <TableHead>
                    <TableRow>
                      {columns.map((column) => (
                        <TableCell
                          sx={{ fontSize: "1.2rem", fontWeight: "bold" }}
                          key={column.id}
                          align={column.align}
                          style={{ minWidth: column.minWidth }}
                        >
                          {column.label}
                        </TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {allDataTask
                      .slice(
                        page * rowsPerPage,
                        page * rowsPerPage + rowsPerPage
                      )
                      .map((dataTask, index) => {
                        return (
                          <TableRow
                            hover
                            role="checkbox"
                            tabIndex={-1}
                            key={index}
                          >
                            {columns.map((column) => {
                              const value = dataTask[column.id];
                              return column.label.toLowerCase() === "option" &&
                                localStorage.getItem("role") === "manager" ? (
                                <TableCell
                                  sx={{
                                    gap: "2rem",
                                  }}
                                  key={column.id}
                                  align={column.align}
                                >
                                  <DeleteTask taskId={dataTask["taskId"]} />
                                </TableCell>
                              ) : (
                                <TableCell
                                  sx={{
                                    fontSize: "1rem",
                                  }}
                                  key={column.id}
                                  align={column.align}
                                >
                                  <Box
                                    sx={{
                                      overflow: "hidden",
                                      height: "1.2rem",
                                    }}
                                  >
                                    <Typography>
                                      <Link
                                        sx={{ color: "black" }}
                                        component="button"
                                        onClick={() =>
                                          handleNavigate(
                                            dataTask["taskId"],
                                            dataTask["taskContent"],
                                            dataTask["classificationTag"],
                                            dataTask["classificationId"]
                                          )
                                        }
                                        underline="s"
                                      >
                                        {value}
                                      </Link>
                                    </Typography>
                                  </Box>
                                </TableCell>
                              );
                            })}
                          </TableRow>
                        );
                      })}
                  </TableBody>
                </Table>
              </TableContainer>
              <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={allDataTask.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Paper>
          </Box>
        ) : (
          <Box>
            <h2>No Tasks Available for this Project</h2>
          </Box>
        )}
      </Box>
    </>
  );
}

export default DataTask;
