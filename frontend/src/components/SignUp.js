import React, { useState } from "react";
import { Link } from "react-router-dom";
import TextField from "@mui/material/TextField";
import {
  Container,
  Button,
  ToggleButtonGroup,
  ToggleButton,
  Typography,
} from "@mui/material";
import axios from "axios";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { BASEURL, REGISTER } from "./Url";
import validator from "validator";

function SignUp() {
  const [role, setRole] = useState("manager");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const resisterUrl = `${BASEURL + REGISTER}`;

  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleChange = (event, roleType) => {
    if (roleType) {
      setRole(roleType);
    }
    console.log(roleType);
  };

  const handleSubmit = async (e) => {
    if (firstName && lastName && email && password && confirmPassword) {
      if (password === confirmPassword) {
        if (validator.isEmail(email)) {
          await axios({
            method: "POST",
            url: resisterUrl,
            headers: {
              "Content-Type": "application/json",
            },
            data: {
              firstName: firstName,
              lastName: lastName,
              email: email,
              password: password,
              role: role,
            },
          })
            .then(function (response) {
              //handle success

              console.log(response);
              window.location.href = "/signin";
            })
            .catch(function (response) {
              //handle error
              console.log(response);
              toast.error(response.response.data.message, {
                autoClose: 2000,
                position: toast.POSITION.BOTTOM_LEFT,
              });
            });
        } else {
          toast.error("Please enter valid Email", {
            autoClose: 2000,
            position: toast.POSITION.BOTTOM_LEFT,
          });
        }
      } else {
        toast.error("Password does not match", {
          autoClose: 2000,
          position: toast.POSITION.BOTTOM_LEFT,
        });
      }
    } else {
      toast.error("Please add all required details", {
        autoClose: 2000,
        position: toast.POSITION.BOTTOM_LEFT,
      });
    }
  };

  return (
    <Container sx={containerStyle}>
      <Typography variant="h4" sx={logoStyle}>
        DataNotion
      </Typography>
      <ToggleButtonGroup
        color="info"
        value={role}
        exclusive
        onChange={handleChange}
        aria-label="Platform"
        sx={{ paddingBottom: "1rem" }}
      >
        <ToggleButton value="manager">Manager</ToggleButton>
        <ToggleButton value="annotator">Annotator</ToggleButton>
      </ToggleButtonGroup>

      <TextField
        id="outlined-basic"
        required
        label="First Name"
        variant="outlined"
        sx={textFieldStyle}
        onChange={handleFirstNameChange}
      />
      <TextField
        id="outlined-basic"
        required
        label="Last Name"
        variant="outlined"
        sx={textFieldStyle}
        onChange={handleLastNameChange}
      />
      <TextField
        id="outlined-basic"
        required
        label="Email"
        variant="outlined"
        sx={textFieldStyle}
        onChange={handleEmailChange}
      />
      <TextField
        id="outlined-basic"
        label="Password"
        type="password"
        required
        variant="outlined"
        sx={textFieldStyle}
        onChange={handlePasswordChange}
      />
      <TextField
        id="outlined-basic"
        required
        label="Confirm Password"
        type="password"
        variant="outlined"
        sx={textFieldStyle}
        onChange={handleConfirmPasswordChange}
      />
      <Button
        variant="contained"
        size="large"
        sx={buttonStyle}
        onClick={() => handleSubmit()}
      >
        Sign Up
      </Button>
      <Link to="/signin">Already have an account? Login</Link>
    </Container>
  );
}

const logoStyle = {
  marginBottom: "1rem",
};

const containerStyle = {
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  flexDirection: "column",
  height: "100vh",
};

const textFieldStyle = {
  width: "35%",
  paddingBottom: "1rem",
};

const buttonStyle = {
  background: "#19AAA1",
  textTransform: "none",
  fontSize: "1.5rem",
  padding: "0.4rem 2.8rem",
  marginBottom: "0.7rem",
};

export default SignUp;
