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
import AddProject from "./AddProject";
import EditProject from "./EditProject";
import DeleteProject from "./DeleteProject";
import Header from "../Header";
import { useLocation, useNavigate } from "react-router-dom";
import { BASEURL, PROJECTS } from "../Url";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function ProjectDirectory() {
  const url = `${BASEURL + PROJECTS}`;
  const [allProjects, setAllProjects] = useState([]);
  const navigate = useNavigate();
  const location = useLocation();

  const getAllProjects = async () => {
    await axios({
      method: "get",
      url: url,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setAllProjects(response.data);
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
    { id: "id", label: "ID", minWidth: 170, align: "center" },
    {
      id: "name",
      label: "PROJECT NAME",
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

  const handleNavigate = (id, data) => {
    navigate(`${location.pathname}/${id}/tasks`, {
      state: {
        id,
        data,
      },
    });
  };

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  useEffect(() => {
    getAllProjects();
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
          <h2>Projects Directory</h2>
          {localStorage.getItem("role") === "manager" ? <AddProject /> : <></>}
        </Box>
      </Box>
      <Box className="table" sx={{ display: "flex", justifyContent: "center" }}>
        <Paper sx={{ width: "90%", overflow: "hidden" }}>
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
                {allProjects
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((project, index) => {
                    return (
                      <TableRow hover role="checkbox" tabIndex={-1} key={index}>
                        {columns.map((column) => {
                          const value = project[column.id];
                          return column.label.toLowerCase() === "option" &&
                            localStorage.getItem("role") === "manager" ? (
                            <TableCell
                              sx={{
                                display: "flex",
                                justifyContent: "center",
                                gap: "2rem",
                              }}
                              key={column.id}
                              align={column.align}
                            >
                              <EditProject projectId={project["id"]} />
                              <DeleteProject projectId={project["id"]} />
                            </TableCell>
                          ) : (
                            <TableCell
                              sx={fontSize}
                              key={column.id}
                              align={column.align}
                            >
                              <Box
                                sx={{
                                  overflow: "hidden",
                                }}
                              >
                                <Typography sx={{ color: "black" }}>
                                  <Link
                                    sx={{ color: "black" }}
                                    component="button"
                                    onClick={() =>
                                      handleNavigate(project["id"])
                                    }
                                    underline="none"
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
            count={allProjects.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Paper>
      </Box>
    </>
  );
}

const fontSize = {
  fontSize: "1.1rem",
};

export default ProjectDirectory;
