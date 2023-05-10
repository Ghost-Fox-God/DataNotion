import { Box } from "@mui/material";
import React from "react";
import { useState } from "react";
import axios from "axios";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ImportAddAnnotator from "./ImportAddAnnotator";
import { BASEURL, IMPORTS, PROJECTS } from "../Url";
import { useParams } from "react-router-dom";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 1000,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function Import() {
  let { projectId } = useParams();
  const fileUploadUrl = `${BASEURL + PROJECTS}/${projectId + IMPORTS}`;
  const [selectedFile, setSelectedFile] = useState(null);
  const [annotators, setAnnotators] = useState([]);
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const getAnnotators = (result) => {
    let squares = result.map((num) => num.id);
    setAnnotators(squares);
  };

  const onFileChange = (e) => {
    e.preventDefault();
    setSelectedFile(e.target.files[0]);
  };

  const onFileUpload = async () => {
    {
      selectedFile && annotators.length > 0
        ? await axios({
            method: "post",
            url: fileUploadUrl,
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${localStorage.getItem("auth_token")}`,
            },
            data: {
              file: selectedFile,
              assignTo: [...new Set(annotators)].join(","),
            },
          })
            .then(function (response) {
              //handle success
              console.log(response);
              window.location.reload("false");
              handleClose();
            })
            .catch(function (response) {
              //handle error
              console.log(response);
              toast.error(response.response.data.message, {
                autoClose: 2000,
                position: toast.POSITION.BOTTOM_LEFT,
              });
            })
        : toast.error("File or Annotator is missing", {
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
        Import Data
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
            <Box>
              <input type="file" onChange={onFileChange} />
            </Box>
            <ImportAddAnnotator getAnnotators={getAnnotators} />
            <Box sx={{ display: "flex", justifyContent: "space-around" }}>
              <Button
                onClick={onFileUpload}
                variant="contained"
                size="large"
                sx={buttonStyle}
              >
                Upload!
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

export default Import;
