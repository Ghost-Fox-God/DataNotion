import React, { useEffect, useState } from "react";
import { Box, Divider } from "@mui/material";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import axios from "axios";
import ClearIcon from "@mui/icons-material/Clear";
import AddAnnotator from "./AddAnnotator";
import AddManager from "./AddManager";
import { BASEURL, MANAGERS, ANNOTATORS, PROJECTS, USERS } from "../Url";
import Header from "../Header";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function ProjectManagement() {
  let { projectId } = useParams();
  const annotatorsUrl = `${BASEURL + PROJECTS}/${projectId + ANNOTATORS}`;
  const managersUrl = `${BASEURL + PROJECTS}/${projectId + MANAGERS}`;
  const deleteUserUrl = `${BASEURL + PROJECTS}/${projectId + USERS}`;
  const [allAnnotators, setAllAnnotators] = useState([]);
  const [allManagers, setAllManagers] = useState([]);

  const getAllAnnotators = async () => {
    await axios({
      method: "get",
      url: annotatorsUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setAllAnnotators(response.data);
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const getAllManagers = async () => {
    await axios({
      method: "get",
      url: managersUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setAllManagers(response.data);
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const annotatorColumns = [
    { id: "id", label: "ID", minWidth: 170, align: "center" },
    {
      id: "fullName",
      label: "ANNOTATOR NAME",
      minWidth: 100,
      align: "center",
    },
    {
      id: "option",
      label: "OPTION",
      minWidth: 100,
      align: "center",
    },
  ];

  const managerColumns = [
    { id: "id", label: "ID", minWidth: 170, align: "center" },
    {
      id: "fullName",
      label: "MANAGER NAME",
      minWidth: 100,
      align: "center",
    },
    {
      id: "option",
      label: "OPTION",
      minWidth: 100,
      align: "center",
    },
  ];

  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleDeleteAnnotator = async (id) => {
    await axios({
      method: "delete",
      url: deleteUserUrl,
      data: {
        projectId,
        userId: id,
      },
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
    getAllAnnotators();
  };

  const handleDeleteManager = async (id) => {
    await axios({
      method: "delete",
      url: deleteUserUrl,
      data: {
        projectId,
        userId: id,
      },
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
    getAllManagers();
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  useEffect(() => {
    getAllAnnotators();
    getAllManagers();
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
          <h2>Project Management</h2>
          {/* <AddProject /> */}
        </Box>
      </Box>
      <Box
        sx={{
          width: "80%",
          height: "80vh",
          margin: "auto",
          display: "flex",
          justifyContent: "space-between",
        }}
      >
        <Box sx={{ width: "45%" }}>
          <AddAnnotator getAllAnnotators={getAllAnnotators} />
          <Box
            className="table"
            sx={{
              display: "flex",
              justifyContent: "center",
              marginTop: "2rem",
            }}
          >
            <Paper sx={{ width: "100%", overflow: "hidden" }}>
              <TableContainer sx={{ maxHeight: 600 }}>
                <Table stickyHeader aria-label="sticky table">
                  <TableHead>
                    <TableRow>
                      {annotatorColumns.map((column) => (
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
                    {allAnnotators
                      .slice(
                        page * rowsPerPage,
                        page * rowsPerPage + rowsPerPage
                      )
                      .map((annotators, index) => {
                        return (
                          <TableRow
                            hover
                            role="checkbox"
                            tabIndex={-1}
                            key={index}
                          >
                            {annotatorColumns.map((column) => {
                              const value = annotators[column.id];
                              return column.label.toLowerCase() === "option" ? (
                                <TableCell
                                  sx={{
                                    gap: "2rem",
                                  }}
                                  key={column.id}
                                  align={column.align}
                                >
                                  <ClearIcon
                                    onClick={() =>
                                      handleDeleteAnnotator(annotators["id"])
                                    }
                                    sx={{ color: "red" }}
                                  />
                                </TableCell>
                              ) : (
                                <TableCell
                                  sx={fontSize}
                                  key={column.id}
                                  align={column.align}
                                >
                                  <p>{value}</p>
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
                count={allAnnotators.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Paper>
          </Box>
        </Box>
        <Divider orientation="vertical" flexItem />
        <Box sx={{ width: "45%" }}>
          <AddManager getAllManagers={getAllManagers} />
          <Paper
            sx={{
              width: "100%",
              overflow: "hidden",
              marginTop: "2rem",
            }}
          >
            <TableContainer sx={{ maxHeight: 600 }}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    {managerColumns.map((column) => (
                      <TableCell
                        sx={{
                          fontSize: "1.2rem",
                          fontWeight: "bold",
                        }}
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
                  {allManagers
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((managers, index) => {
                      return (
                        <TableRow
                          hover
                          role="checkbox"
                          tabIndex={-1}
                          key={index}
                        >
                          {managerColumns.map((column) => {
                            const value = managers[column.id];
                            return column.label.toLowerCase() === "option" ? (
                              <TableCell
                                sx={{
                                  gap: "2rem",
                                }}
                                key={column.id}
                                align={column.align}
                              >
                                <ClearIcon
                                  onClick={() =>
                                    handleDeleteManager(managers["id"])
                                  }
                                  sx={{ color: "red" }}
                                />
                              </TableCell>
                            ) : (
                              <TableCell
                                sx={fontSize}
                                key={column.id}
                                align={column.align}
                              >
                                <p>{value}</p>
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
              count={allManagers.length}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </Paper>
        </Box>
      </Box>
    </>
  );
}

const fontSize = {
  fontSize: "1.1rem",
};

export default ProjectManagement;
