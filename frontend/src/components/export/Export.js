import { Box, ToggleButton, ToggleButtonGroup, Button } from "@mui/material";
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
import { toast } from "react-toastify";
import { useParams } from "react-router-dom";
import { BASEURL, EXPORTS, PROJECTS } from "../Url";

function Export() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [exportHistory, setExportHistory] = useState([]);
  const { projectId } = useParams();
  const exportHistoryUrl = `${BASEURL + PROJECTS}/${projectId + EXPORTS}`;
  const exportUrl = `${BASEURL + PROJECTS}/${projectId + EXPORTS}`;

  const getExportHistory = async () => {
    await axios({
      method: "get",
      url: exportHistoryUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setExportHistory(response.data);
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

  const handleDownload = async (id) => {
    fetch(`${exportUrl}/${id}`, {
      method: "GET",
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then((response) => response.blob())
      .then((response) => {
        const blob = new Blob([response], {
          type: "application/json",
        });
        const downloadUrl = URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = downloadUrl;
        a.download = "export.json";
        document.body.appendChild(a);
        a.click();
      });
  };

  const handleExport = async () => {
    await axios({
      method: "post",
      url: exportHistoryUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        getExportHistory();
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response, {
          autoClose: 2000,
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const handleImportExportToggle = () => {
    window.location.href = `/projects/${projectId}/imports`;
  };

  const columns = [
    { id: "id", label: "ID", minWidth: 170, align: "center" },
    {
      id: "exportDate",
      label: "EXPORT DATE",
      minWidth: 100,
      align: "center",
    },
    {
      id: "status",
      label: "STATUS",
      minWidth: 170,
      align: "center",
    },
    {
      id: "option",
      label: "OPTION",
      minWidth: 170,
      align: "center",
    },
  ];

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };
  useEffect(() => {
    getExportHistory();
  }, []);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  return (
    <Box>
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
            value="export"
            exclusive
            onChange={() => handleImportExportToggle()}
            aria-label="Platform"
            sx={{ paddingBottom: "1rem" }}
          >
            <ToggleButton value="import">Import</ToggleButton>
            <ToggleButton value="export">Export</ToggleButton>
          </ToggleButtonGroup>
        </Box>
        <Box>
          <Button
            onClick={() => handleExport()}
            variant="contained"
            size="large"
            sx={buttonStyle}
          >
            EXPORT DATA
          </Button>
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
                {exportHistory
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((history, index) => {
                    return (
                      <TableRow hover role="checkbox" tabIndex={-1} key={index}>
                        {columns.map((column) => {
                          const value = history[column.id];
                          return column.label.toLowerCase() === "option" ? (
                            history.status === "Done" ? (
                              <TableCell
                                sx={{
                                  display: "flex",
                                  justifyContent: "center",
                                  gap: "2rem",
                                }}
                                key={column.id}
                                align={column.align}
                              >
                                <Button
                                  onClick={() => handleDownload(history["id"])}
                                  variant="contained"
                                  size="large"
                                  sx={buttonStyle}
                                >
                                  Download
                                </Button>
                              </TableCell>
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
            count={exportHistory.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Paper>
      </Box>
    </Box>
  );
}

const fontSize = {
  fontSize: "1.1rem",
};

const buttonStyle = {
  background: "#19AAA1",
  textTransform: "none",
  fontSize: "1.5rem",
  padding: "0.4rem 2.8rem",
  marginBottom: "0.7rem",
  marginTop: "1rem",
};

export default Export;
