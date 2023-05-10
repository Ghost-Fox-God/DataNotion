import { Box } from "@mui/material";
import React, { useState, useEffect } from "react";
import Chart from "react-apexcharts";

function EntityPiechart({ chartsData }) {
  const [entityTag, setEntityTag] = useState([]);
  const [tagCount, setTagCount] = useState([]);

  useEffect(() => {
    const sEntityTag = [];
    const sTagCount = [];
    const getEntityData = async () => {
      const resData = chartsData.entityStatistics;
      for (let i = 0; i < resData.length; i++) {
        sEntityTag.push(resData[i].tagName);
        sTagCount.push(parseInt(resData[i].tagCount));
      }
      setEntityTag(sEntityTag);
      setTagCount(sTagCount);
    };

    getEntityData();
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
            title: { text: "Entity Tag Distribution" },
            noData: { text: "Empty Data" },
            labels: entityTag,
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
export default EntityPiechart;
