import React from "react";
import { Container, Box, Divider } from "@mui/material";
import { Link } from "react-router-dom";
import LogoutIcon from "@mui/icons-material/Logout";

function Header() {
  const handleLogout = () => {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("role");
    window.location.href = "/signin";
  };
  return (
    <>
      <Box
        className="header"
        sx={{
          display: "flex",
          justifyContent: "flex-end",
          alignItems: "center",
          height: "6vh",
          width: "100%",
        }}
      >
        <Container
          className="header-left"
          sx={{
            display: "flex",
            justifyContent: "flex-start",
            alignItems: "center",
          }}
        >
          <Box sx={{ paddingRight: "1rem" }}>
            <h3>DataNotion</h3>
          </Box>
        </Container>
        <Container
          className="header-right"
          sx={{
            display: "flex",
            justifyContent: "end",
            alignItems: "center",
            gap: "1rem",
          }}
        >
          <Link component="button" to="/projects">
            Project Directory
          </Link>
          <Box>
            <LogoutIcon onClick={() => handleLogout()} />
          </Box>
        </Container>
      </Box>
      <Divider />
    </>
  );
}

export default Header;
