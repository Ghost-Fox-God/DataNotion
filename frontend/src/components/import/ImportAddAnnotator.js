import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import axios from "axios";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { BASEURL, ANNOTATORS, PROJECTS } from "../Url";
import { useParams } from "react-router-dom";
import ClearIcon from "@mui/icons-material/Clear";
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

function ImportAddAnnotator({ getAnnotators }) {
  let { projectId } = useParams();
  const allAnnotatorsUrl = `${BASEURL + PROJECTS}/${projectId + ANNOTATORS}`;
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    handleDropDown();
    setOpen(true);
  };
  const handleClose = () => setOpen(false);
  const [allAnnotators, setAllAnnotators] = useState([]);
  const [userData, setUserData] = useState("");
  const [error, setError] = useState("");
  const [userList, setUserList] = useState([]);

  const handleDropDown = async () => {
    await axios({
      method: "get",
      url: allAnnotatorsUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        setAllAnnotators(response.data);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
        setError(response.message);
      });
  };

  const handleChange = (event) => {
    event.preventDefault();
    let data = event.target.value;
    console.log("yes");
    setUserData(data.fullName + "  " + data.id);
    setUserList((prev) => [...prev, data]);
  };

  const handleSave = async () => {
    handleClose();
  };

  const handleDeleteAnnotator = (single) => {
    setUserList(userList.filter((item) => item.id !== single.id));
  };

  useEffect(() => {
    getAnnotators(userList);
  }, [userList]);

  return (
    <Box>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Button
          onClick={handleOpen}
          variant="contained"
          size="large"
          sx={buttonStyle}
        >
          Add Annotator
        </Button>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "flex-end",
            gap: "0",
            margin: "2rem",
            padding: "0",
          }}
        >
          {userList.length === 0 ? <></> : <p>Selected Annotators</p>}
          {userList.map((single, index) => (
            <Box key={index} sx={{ display: "flex", gap: "2rem" }}>
              <h3>{single.fullName + " (" + single.id + ")"}</h3>
              <ClearIcon
                onClick={() => handleDeleteAnnotator(single)}
                sx={{ color: "red" }}
              />
            </Box>
          ))}
        </Box>
        <Typography sx={{ color: "red" }}>{error}</Typography>
      </Box>
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
              <FormControl sx={{ m: 1, minWidth: 300 }}>
                <InputLabel id="demo-simple-select-autowidth-label">
                  User Id
                </InputLabel>
                <Select
                  labelId="demo-simple-select-autowidth-label"
                  id="demo-simple-select-autowidth"
                  value={userData.fullName}
                  onChange={handleChange}
                  autoWidth
                  label="User Id"
                  defaultValue=""
                >
                  {allAnnotators ? (
                    allAnnotators.map((annotator, index) => {
                      return (
                        <MenuItem key={index} value={annotator}>
                          {annotator.fullName + " (" + annotator.id + ")"}
                        </MenuItem>
                      );
                    })
                  ) : (
                    <MenuItem onClick={() => handleClose()} value="">
                      <em>None</em>
                    </MenuItem>
                  )}
                </Select>
              </FormControl>
            </Box>
            <Box sx={{ display: "flex", justifyContent: "space-around" }}>
              <Button
                onClick={handleSave}
                variant="contained"
                size="large"
                sx={buttonStyle}
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
        </Box>
      </Modal>
    </Box>
  );
}

const buttonStyle = {
  background: "#2FC309",
  textTransform: "none",
  fontSize: "1.5rem",
  padding: "0.4rem 2.8rem",
  marginBottom: "0.7rem",
  marginTop: "1rem",
};

export default ImportAddAnnotator;
