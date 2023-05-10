import { Box } from "@mui/material";
import React, { useState, useEffect } from "react";
import Chart from "react-apexcharts";

function ClassificationPiechart({ chartsData }) {
  const [classificationTag, setClassificationTag] = useState([]);
  const [tagCount, setTagCount] = useState([]);
  const sClassificationTag = [];
  const sTagCount = [];

  useEffect(() => {
    const resData = chartsData.classificationStatistics;
    for (let i = 0; i < resData.length; i++) {
      sClassificationTag.push(resData[i].tagName);
      sTagCount.push(parseInt(resData[i].tagCount));
    }
    setClassificationTag(sClassificationTag);
    setTagCount(sTagCount);
  }, []);

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div className="container-fluid mb-3">
        <Chart
          type="pie"
          width="180%"
          height="300%"
          series={tagCount}
          options={{
            title: { text: "Classification Tag Distribution" },
            noData: { text: "Empty Data" },
            labels: classificationTag,
            legend: {
              position: "left",
              onItemHover: {
                highlightDataSeries: true,
              },
            },
          }}
        ></Chart>
      </div>
    </Box>
  );
}
export default ClassificationPiechart;
