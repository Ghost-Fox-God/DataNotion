import { Box, ToggleButton, ToggleButtonGroup } from "@mui/material";
import React, { useEffect, useState } from "react";
import Header from "../Header";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import axios from "axios";
import Import from "./Import";
import { useParams } from "react-router-dom";
import { BASEURL, IMPORTS, PROJECTS } from "../Url";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function ImportScreen() {
  let { projectId } = useParams();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [importHistory, setImportHistory] = useState([]);
  const importHistoryUrl = `${BASEURL + PROJECTS}/${projectId + IMPORTS}`;

  const getImportHistory = async () => {
    await axios({
      method: "get",
      url: importHistoryUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setImportHistory(response.data);
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

  const columns = [
    { id: "id", label: "ID", minWidth: 170, align: "center" },
    {
      id: "importDate",
      label: "IMPORT DATE",
      minWidth: 100,
      align: "center",
    },
    {
      id: "fileName",
      label: "PROJECT NAME",
      minWidth: 100,
      align: "center",
    },
    {
      id: "status",
      label: "STATUS",
      minWidth: 170,
      align: "center",
    },
  ];

  const handleImportExportToggle = () => {
    console.log(projectId);
    window.location.href = `/projects/${projectId}/exports`;
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  useEffect(() => {
    getImportHistory();
  }, []);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  return (
    <>
      <Header />
      <Box
        sx={{
          display: "flex",
          width: "90%",
          margin: "auto",
          marginTop: "3rem",
          justifyContent: "space-between",
        }}
      >
        <Box>
          <ToggleButtonGroup
            color="info"
            value="import"
            exclusive
            onChange={handleImportExportToggle}
            aria-label="Platform"
            sx={{ paddingBottom: "1rem" }}
          >
            <ToggleButton value="import">Import</ToggleButton>
            <ToggleButton value="export">Export</ToggleButton>
          </ToggleButtonGroup>
        </Box>
        <Box>
          <Import />
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
                {importHistory
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((history, index) => {
                    return (
                      <TableRow hover role="checkbox" tabIndex={-1} key={index}>
                        {columns.map((column) => {
                          const value = history[column.id];
                          return column.label.toLowerCase() === "option" ? (
                            history.status === "COMPLETED" ? (
                              <TableCell
                                sx={{
                                  display: "flex",
                                  justifyContent: "center",
                                  gap: "2rem",
                                }}
                                key={column.id}
                                align={column.align}
                              ></TableCell>
                            ) : (
                              <TableCell
                                sx={fontSize}
                                key={column.id}
                                align={column.align}
                              ></TableCell>
                            )
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
            count={importHistory.length}
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
export default ImportScreen;
