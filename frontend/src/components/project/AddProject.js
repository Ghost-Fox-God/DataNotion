import React, { useState } from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import TextField from "@mui/material/TextField";
import axios from "axios";
import { BASEURL, PROJECTS } from "../Url";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

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

function AddProject() {
  const addProjectUrl = `${BASEURL + PROJECTS}`;
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
        method: "post",
        url: addProjectUrl,
        data: {
          projectName,
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

  return (
    <Box>
      <Button
        onClick={handleOpen}
        variant="contained"
        size="large"
        sx={buttonStyle}
      >
        Add Project
      </Button>
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
            <TextField
              required
              id="projectName"
              label="PROJECT NAME"
              defaultValue=""
              fullWidth
              onChange={handleOnChange}
            />
          </Box>
          <Box sx={{ display: "flex", justifyContent: "space-around" }}>
            <Button
              onClick={handleSave}
              variant="contained"
              size="large"
              sx={buttonStyle}
              type="submit"
            >
              Save
            </Button>
            <Button
              onClick={handleClose}
              variant="contained"
              size="large"
              sx={buttonStyle}
            >
              Cancel
            </Button>
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

export default AddProject;
