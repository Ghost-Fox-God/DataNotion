import React, { useState } from "react";
import { TextAnnotator } from "react-text-annotate";
import { BASEURL, CLASSIFICATION, ENTITY, PROJECTS, TASKS } from "../Url";
import { Box, Typography } from "@mui/material";
import axios from "axios";
import { useEffect } from "react";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import ConfirmAction from "./ConfirmAction";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Card = ({ children }) => (
  <div
    style={{
      boxShadow: "0 2px 4px rgba(0,0,0,.1)",
      margin: 6,
      maxWidth: "150rem",
      padding: 16,
    }}
  >
    {children}
  </div>
);

function Annotation({
  textData,
  entityTags,
  classificationTags,
  selectedTag,
  selectedTagId,
}) {
  let { projectId, taskId } = useParams();
  const [currentClassificationTag, setCurrentClassificationTag] = useState("");
  const [value, setValue] = useState({});
  const annotationDataUrl = `${BASEURL + PROJECTS}/${projectId + TASKS}/${
    taskId + ENTITY
  }`;
  const classificationDataUrl = `${BASEURL + PROJECTS}/${projectId + TASKS}/${
    taskId + CLASSIFICATION
  }`;

  const [selectedClassificationTag, setSelectedClassificationTag] = useState(0);
  const [tagColor, setTagColor] = useState({});

  const randomColor = () => {
    let n = (Math.random() * 0xfffff * 1000000).toString(16);
    return "#" + n.slice(0, 6) + "50";
  };

  async function setCol() {
    entityTags.forEach((element) => {
      setTagColor((prev) => ({
        ...prev,
        [element.tagName]: randomColor(),
      }));
    });
  }

  const getAnnotateData = async () => {
    await axios({
      method: "get",
      url: annotationDataUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success
        setValue(response.data);
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

  const getClassificationData = async () => {
    await axios({
      method: "get",
      url: classificationDataUrl,
      headers: {
        authorization: `Bearer ${localStorage.getItem("auth_token")}`,
      },
    })
      .then(function (response) {
        //handle success

        console.log("uyasgdsdgasgu");
        setSelectedClassificationTag(response.data[0].id);
        setCurrentClassificationTag(response.data[0].tagName);
        console.log(response);
      })
      .catch(function (response) {
        //handle error
        console.log(response);
      });
  };

  const [tag, setTag] = useState(0);
  const [tagId, settagId] = useState("");

  const handleChange = (value) => {
    setValue(value);
  };

  const handleTagChange = (e) => {
    let id = e.target.value;
    e.preventDefault();
    setTag(id);
    settagId(entityTags[id - 1].tagName);
  };

  const handleOnChange = (e) => {
    setSelectedClassificationTag(parseInt(e.target.value));
  };

  useEffect(() => {
    setCol();
  }, [entityTags]);

  useEffect(() => {
    getAnnotateData();
    getClassificationData();
  }, []);

  return (
    <div
      style={{
        padding: "0rem 4rem",
        fontFamily: "IBM Plex Sans",
      }}
    >
      <Typography sx={{ marginBottom: "1.5rem" }} variant="h4">
        Classification Tags
      </Typography>
      {classificationTags ? (
        <Box sx={{ marginBottom: "2rem" }}>
          <RadioGroup
            row
            aria-labelledby="demo-row-radio-buttons-group-label"
            name="row-radio-buttons-group"
            defaultValue={selectedTagId}
          >
            {classificationTags.map((classificationTag, index) => (
              <FormControlLabel
                key={classificationTag.id}
                value={classificationTag.id}
                control={<Radio />}
                label={classificationTag.tagName}
                onChange={handleOnChange}
              />
            ))}
          </RadioGroup>
          <h2>Classification Value Was: {currentClassificationTag}</h2>
        </Box>
      ) : (
        <p>No Classification Tags </p>
      )}
      {/* <Classification /> */}
      <div style={{ display: "flex", marginBottom: 24, border: "solid 2px" }}>
        <Card>
          <h3>Choose Entity Tag</h3>
          <select
            style={{
              marginBottom: "2rem",
              padding: "1rem",
              fontSize: "1.3rem",
            }}
            onChange={handleTagChange}
          >
            {entityTags.map((entityTag, index) => (
              <option key={entityTag.id} value={index}>
                {entityTag.tagName}
              </option>
            ))}
          </select>
          <TextAnnotator
            style={{
              fontFamily: "IBM Plex Sans",
              fontSize: "1.5rem",
              lineHeight: 1.5,
            }}
            content={textData}
            value={value}
            onChange={handleChange}
            getSpan={(span) => ({
              ...span,
              tagId: entityTags[tag].id,
              tag: entityTags[tag].tagName,
              color: tagColor[entityTags[tag].tagName],
            })}
          />
        </Card>
      </div>
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <ConfirmAction
          entityMapping={JSON.stringify(value, null, 2)}
          projectId={projectId}
          taskId={taskId}
          classificationId={selectedClassificationTag}
        />
      </Box>
      <Card>
        <h4>Current Value</h4>
        <pre>{JSON.stringify(value, null, 2)}</pre>
      </Card>
    </div>
  );
}

export default Annotation;
