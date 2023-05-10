import { Box, Divider } from "@mui/material";
import React, { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import Header from "../Header";
import { BASEURL, PROJECTS, STATISTICS } from "../Url";
import ClassificationPiechart from "./ClassificationPiechart";
import EntityPiechart from "./EntityPiechart";
import TaskCompletionchart from "./TaskCompletionchart";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function ChartScreen() {
  let { projectId } = useParams();
  const chartsUrl = `${BASEURL + PROJECTS}/${projectId + STATISTICS}`;
  const [chartsData, setChartsData] = useState();

  const getChartsData = async () => {
    await axios({
      method: "get",
      url: chartsUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setChartsData(response.data);
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
    getChartsData();
  }, []);
  return (
    <Box>
      <Header />
      {chartsData ? (
        <Box>
          <Box>
            <TaskCompletionchart chartsData={chartsData} />
          </Box>
          <Divider />
          <Box
            sx={{
              display: "flex",
              width: "100%",
              margin: "auto",
              marginTop: "5rem",
              justifyContent: "space-around",
            }}
          >
            <EntityPiechart chartsData={chartsData} />
            <ClassificationPiechart chartsData={chartsData} />
          </Box>
        </Box>
      ) : (
        <Box></Box>
      )}
    </Box>
  );
}

export default ChartScreen;
