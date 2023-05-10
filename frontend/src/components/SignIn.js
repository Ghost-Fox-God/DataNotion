import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import TextField from "@mui/material/TextField";
import { Container, Button, Typography } from "@mui/material";
import axios from "axios";
import jwt_decode from "jwt-decode";
import { toast } from "react-toastify";
import { BASEURL, LOGIN } from "./Url";
import validator from "validator";

function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const loginUrl = `${BASEURL + LOGIN}`;

  const handleEmailChange = (e) => {
    e.preventDefault();
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    e.preventDefault();
    setPassword(e.target.value);
  };

  const setAuthToken = (token) => {
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    } else delete axios.defaults.headers.common["Authorization"];
  };

  const handleSubmit = async () => {
    if (validator.isEmail(email) && password) {
      await axios({
        method: "POST",
        url: loginUrl,
        headers: {
          "Content-Type": "application/json",
        },
        data: {
          email,
          password,
        },
      })
        .then(function (response) {
          //handle success
          const token = response.data.access_token;
          var decoded = jwt_decode(token);
          // @ts-ignore
          // @ts-ignore
          localStorage.setItem("role", decoded.role[0]);
          localStorage.setItem("auth_token", token);
          setAuthToken(token);
          window.location.href = "/projects";
        })
        .catch(function (response) {
          //handle error
          console.log(response);
          toast.error("Invalid email or password", {
            autoClose: 2000,
            position: toast.POSITION.BOTTOM_LEFT,
          });
        });
    } else {
      toast.error("Please enter valid email and password", {
        autoClose: 2000,
        position: toast.POSITION.BOTTOM_LEFT,
      });
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("auth_token");
    if (token) {
      window.location.href = "/projects";
    }
  }, []);

  return (
    <Container sx={containerStyle}>
      <Typography variant="h4" sx={logoStyle}>
        DataNotion
      </Typography>
      <TextField
        id="outlined-basic"
        label="Email"
        required
        variant="outlined"
        onChange={handleEmailChange}
        sx={textFieldStyle}
      />
      <TextField
        id="outlined-basic"
        label="Password"
        type="password"
        required
        variant="outlined"
        onChange={handlePasswordChange}
        sx={textFieldStyle}
      />
      <Button
        variant="contained"
        size="large"
        sx={buttonStyle}
        onClick={handleSubmit}
      >
        Login
      </Button>
      <Link to="/signup">Don't have account? SignUp</Link>
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

export default SignIn;
