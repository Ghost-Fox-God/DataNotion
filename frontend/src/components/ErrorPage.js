import React from "react";
import { Box, Typography } from "@mui/material";
import { Link } from "react-router-dom";

function ErrorPage() {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
        minHeight: "100vh",
      }}
    >
      <Typography variant="h1">404</Typography>
      <Typography variant="h6">
        The page you’re looking for doesn’t exist.
      </Typography>
      <Link to="/signin">Back to Homepage</Link>
    </Box>
  );
}

export default ErrorPage;
