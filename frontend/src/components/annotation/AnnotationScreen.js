import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import { BASEURL, PROJECTS, ENTITY, CLASSIFICATION } from "../Url";
import { useLocation, useParams, Link } from "react-router-dom";
import axios from "axios";

import Annotation from "./Annotation";
import Header from "../Header";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function AnnotationScreen() {
  let { projectId } = useParams();
  const entityUrl = `${BASEURL + PROJECTS}/${projectId + ENTITY}`;
  const classificationUrl = `${BASEURL + PROJECTS}/${
    projectId + CLASSIFICATION
  }`;
  const [entityTags, setEntityTags] = useState([]);
  const [classificationTags, setClassificationTags] = useState([]);
  const state = useLocation();

  const getEntityTag = async () => {
    await axios({
      method: "get",
      url: entityUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        console.log(response);
        setEntityTags(response.data);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
        toast.error(response.message, {
          position: toast.POSITION.BOTTOM_LEFT,
        });
      });
  };

  const getClassificationTag = async () => {
    await axios({
      method: "get",
      url: classificationUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success

        setClassificationTags(response.data);
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

  useEffect(() => {
    getEntityTag();
    getClassificationTag();
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
          <h2>Annotation</h2>
          <Box>
            <Link component="button" to={`/projects/${projectId}/tasks`}>
              <p style={{ fontSize: "1.4rem" }}>Tasks</p>
            </Link>
          </Box>
        </Box>
      </Box>
      <Annotation
        textData={state.state.data}
        entityTags={entityTags}
        classificationTags={classificationTags}
        selectedTag={state.state.selectedTag}
        selectedTagId={state.state.selectedTagId}
      />
    </>
  );
}

export default AnnotationScreen;
