import React, { useEffect, useState } from "react";
import { Box, Link } from "@mui/material";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import TextField from "@mui/material/TextField";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { BASEURL, PROJECTS } from "../Url";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 500,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function EditProject({ projectId }) {
  const editProjectUrl = `${BASEURL + PROJECTS}/${projectId}`;
  const getProjectByIdUrl = `${BASEURL + PROJECTS}/${projectId}`;

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [projectName, setProjectName] = useState("");

  const handleOnChange = (e) => {
    setProjectName(e.target.value);
  };

  const handleSave = async () => {
    if (projectName) {
      await axios({
        method: "put",
        url: editProjectUrl,
        data: {
          id: projectId,
          name: projectName,
        },
        headers: {
          authorization: `Bearer ${localStorage.getItem("auth_token")}`,
        },
      })
        .then(function (response) {
          //handle success
          console.log(response);
          window.location.reload(false);
          handleClose();
        })
        .catch(function (response) {
          //handle error
          console.log(response);
          toast.error(response.message, {
            position: toast.POSITION.BOTTOM_LEFT,
          });
        });
    } else {
      toast.error("Please add the required field", {
        autoClose: 2000,
        position: toast.POSITION.BOTTOM_LEFT,
      });
    }
  };

  const getProjectById = async () => {
    await axios({
      method: "get",
      url: getProjectByIdUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        setProjectName(response.data.name);
        handleClose();
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  useEffect(() => {
    getProjectById();
  }, []);

  return (
    <Box>
      <Link
        sx={{ fontSize: "1.3rem" }}
        onClick={handleOpen}
        href="#"
        underline="none"
      >
        Edit
      </Link>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Box
            component="form"
            sx={{
              "& .MuiTextField-root": { m: 1, width: "100%" },
            }}
            noValidate
            autoComplete="off"
          >
            <Box>
              <TextField
                InputProps={{ style: { fontSize: "1.3rem" } }}
                required
                id="projectName"
                label="PROJECT NAME"
                fullWidth
                onChange={handleOnChange}
                value={projectName}
              />
            </Box>
            <Box sx={{ display: "flex", justifyContent: "space-around" }}>
              <Button
                onClick={() => handleSave()}
                variant="contained"
                size="large"
                sx={buttonStyle}
              >
                Save
              </Button>
              <Button
                onClick={() => handleClose()}
                variant="contained"
                size="large"
                sx={buttonStyle}
              >
                Cancel
              </Button>
            </Box>
          </Box>
        </Box>
      </Modal>
    </Box>
  );
}

const buttonStyle = {
  background: "#19AAA1",
  textTransform: "none",
  fontSize: "1.5rem",
  padding: "0.4rem 2.8rem",
  marginBottom: "0.7rem",
  marginTop: "1rem",
};

export default EditProject;
