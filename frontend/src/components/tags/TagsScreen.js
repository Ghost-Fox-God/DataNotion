import {
  Box,
  TextField,
  ToggleButton,
  ToggleButtonGroup,
  Button,
  Divider,
} from "@mui/material";
import React, { useState } from "react";
import Header from "../Header";
import axios from "axios";
import { useEffect } from "react";
import ClearIcon from "@mui/icons-material/Clear";
import { BASEURL, CLASSIFICATION, ENTITY, PROJECTS } from "../Url";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function TagsScreen() {
  const [allClassification, setAllClassification] = useState([]);
  const [allEntity, setAllEntity] = useState([]);
  const [tagCategory, setTagCategory] = useState("entity");
  const [newTag, setNewTag] = useState("");
  let { projectId } = useParams();
  const classificationTagsUrl = `${BASEURL + PROJECTS}/${
    projectId + CLASSIFICATION
  }`;
  const entityTagsUrl = `${BASEURL + PROJECTS}/${projectId + ENTITY}`;

  const handleTagTextChange = (e) => {
    console.log(e.target.value);
    e.preventDefault();
    setNewTag(e.target.value);
  };

  const getAllClassification = async () => {
    console.log("class");
    await axios({
      method: "get",
      url: classificationTagsUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setAllClassification(response.data);
        console.log(response.data);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          autoClose: 2000,
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const handleAdd = async () => {
    if (tagCategory === "entity") {
      await axios({
        method: "post",
        url: entityTagsUrl,
        data: {
          tagName: newTag,
          projectId: Number(projectId),
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
            autoClose: 2000,
            position: toast.POSITION.BOTTOM_LEFT,
          });
        });
      getAllEntity();
    } else {
      await axios({
        method: "post",
        url: classificationTagsUrl,
        data: {
          tagName: newTag,
          projectId: Number(projectId),
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
            autoClose: 2000,
            position: toast.POSITION.BOTTOM_LEFT,
          });
        });
      getAllClassification();
    }
  };

  const getAllEntity = async () => {
    await axios({
      method: "get",
      url: entityTagsUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setAllEntity(response.data);
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          autoClose: 2000,
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const handleDeleteClassificationTag = async (id) => {
    console.log(id);
    await axios({
      method: "delete",
      url: `${classificationTagsUrl}/${id}`,
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
          autoClose: 2000,
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
    getAllClassification();
  };

  const handleDeleteEntityTag = async (id) => {
    console.log(id);
    await axios({
      method: "delete",
      url: `${entityTagsUrl}/${id}`,
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
          autoClose: 2000,
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
    getAllEntity();
  };

  const handleChange = (event, tagType) => {
    if (tagType) {
      setTagCategory(tagType);
    }
  };

  useEffect(() => {
    getAllClassification();
    getAllEntity();
  }, []);
  return (
    <>
      <Box>
        <Header />
        <Box
          sx={{
            height: "15vh",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginX: "10rem",
            marginTop: "5rem",
          }}
        >
          <TextField
            id="outlined-basic"
            label="Enter Tag Name"
            required
            variant="outlined"
            onChange={handleTagTextChange}
            value={newTag}
            sx={{ width: "30%" }}
          />
          <p style={{ fontSize: "2rem", marginLeft: "2rem" }}>add as:</p>
          <Box>
            <ToggleButtonGroup
              color="info"
              value={tagCategory}
              exclusive
              onChange={handleChange}
              aria-label="Platform"
              sx={{
                paddingBottom: "1rem",
                width: "20rem",
              }}
            >
              <ToggleButton value="entity" sx={{ width: "100%" }}>
                Entity
              </ToggleButton>
              <ToggleButton sx={{ width: "100%" }} value="classification">
                Classification
              </ToggleButton>
            </ToggleButtonGroup>
          </Box>
          <Box>
            <Button
              onClick={() => handleAdd()}
              variant="contained"
              size="large"
              sx={buttonStyle}
            >
              ADD
            </Button>
          </Box>
        </Box>
      </Box>
      <Box
        sx={{
          width: "80%",
          height: "60vh",
          margin: "auto",
          display: "flex",
          justifyContent: "space-around",
        }}
      >
        <Box>
          <p style={{ fontSize: "2rem" }}>Classification tags</p>
          <Box>
            {allClassification.map((classificationTag, index) => {
              return (
                <Box
                  sx={{
                    margin: "1rem",
                    border: "black solid 2px",
                    padding: "1rem",
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    gap: "3rem",
                  }}
                >
                  <Box>
                    <p style={{ fontSize: "1.5rem" }} key={index}>
                      {classificationTag.tagName}
                    </p>
                  </Box>
                  <ClearIcon
                    onClick={() =>
                      handleDeleteClassificationTag(classificationTag.id)
                    }
                    sx={{ color: "red" }}
                  />
                </Box>
              );
            })}
          </Box>
        </Box>
        <Divider orientation="vertical" flexItem />
        <Box>
          <p style={{ fontSize: "2rem" }}>Entity tags</p>
          <Box>
            {allEntity.map((entityTag, index) => (
              <Box
                sx={{
                  border: "black solid 2px",
                  margin: "1rem",
                  padding: "1rem",
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                  gap: "3rem",
                }}
              >
                <Box>
                  <p style={{ fontSize: "1.5rem" }} key={index}>
                    {entityTag.tagName}
                  </p>
                </Box>

                <ClearIcon
                  onClick={() => handleDeleteEntityTag(entityTag.id)}
                  sx={{ color: "red" }}
                />
              </Box>
            ))}
          </Box>
        </Box>
      </Box>
    </>
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

export default TagsScreen;
