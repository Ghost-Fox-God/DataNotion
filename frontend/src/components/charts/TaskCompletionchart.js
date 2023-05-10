import { Box } from "@mui/material";
import React from "react";
import CountUp from "react-countup";

function TaskCompletionchart({ chartsData }) {
  const totalTasks = chartsData.totalDataTasks;
  const completedTasks = chartsData.annotatedDataTasks;
  const remainingTasks = totalTasks - completedTasks;
  return (
    <Box
      sx={{
        width: "60%",
        margin: "auto",
        padding: "4rem",
        display: "flex",
        justifyContent: "space-around",
      }}
    >
      <Box>
        <h2>Total tasks</h2>
        <CountUp start={0} end={totalTasks} delay={0} duration={0.5}>
          {({ countUpRef }) => (
            <div>
              <span style={{ fontSize: "2rem" }} ref={countUpRef} />
            </div>
          )}
        </CountUp>
      </Box>
      <Box>
        <h2>Completed tasks</h2>
        <CountUp start={0} end={completedTasks} delay={0} duration={0.5}>
          {({ countUpRef }) => (
            <div>
              <span style={{ fontSize: "2rem" }} ref={countUpRef} />
            </div>
          )}
        </CountUp>
      </Box>
      <Box>
        <h2>Remaining tasks</h2>
        <CountUp start={0} end={remainingTasks} delay={0} duration={0.5}>
          {({ countUpRef }) => (
            <div>
              <span style={{ fontSize: "2rem" }} ref={countUpRef} />
            </div>
          )}
        </CountUp>
      </Box>
    </Box>
  );
}
export default TaskCompletionchart;
