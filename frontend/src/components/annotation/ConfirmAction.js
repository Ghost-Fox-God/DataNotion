import React from "react";
import { Box } from "@mui/material";
import Button from "@mui/material/Button";
import Modal from "@mui/material/Modal";
import axios from "axios";
import { BASEURL, CLASSIFICATION, ENTITY, PROJECTS, TASKS } from "../Url";
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

function ConfirmAction({ entityMapping, projectId, taskId, classificationId }) {
  const dataAnnotateUrl = `${BASEURL + PROJECTS}/${
    projectId + TASKS
  }/${taskId}${ENTITY}`;
  const classificationIdUrl = `${BASEURL + PROJECTS}/${
    projectId + TASKS
  }/${taskId}${CLASSIFICATION}`;
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleSave = (e) => {
    handleOpen();
  };

  const handleYes = async () => {
    const data = JSON.parse(entityMapping);

    await axios({
      method: "post",
      url: dataAnnotateUrl,
      data,
      headers: {
        Authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        toast.success("Entity Mapping Success", {
          position: toast.POSITION.BOTTOM_LEFT,
        });
        handleClose();
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
    await axios({
      method: "post",
      url: classificationIdUrl,
      data: {
        classificationId,
      },
      headers: {
        Authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        toast.success("Classification Mapping is success ", {
          position: toast.POSITION.BOTTOM_LEFT,
        });
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

  return (
    <Box>
      <Button
        onClick={() => handleSave()}
        variant="contained"
        size="large"
        sx={buttonStyle}
      >
        Save
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
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              gap: "2rem",
            }}
            noValidate
            autoComplete="off"
          >
            <Box>
              <Box>
                <h3>Are you Sure?</h3>
              </Box>
              <Box sx={{ display: "flex", gap: "2rem" }}>
                <Button
                  onClick={handleYes}
                  variant="contained"
                  size="large"
                  sx={buttonStyle}
                >
                  Yes
                </Button>
                <Button
                  onClick={handleClose}
                  variant="contained"
                  size="large"
                  sx={buttonStyle}
                >
                  No
                </Button>
              </Box>
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

export default ConfirmAction;
