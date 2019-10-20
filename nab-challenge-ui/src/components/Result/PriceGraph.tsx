import React from "react";

import "react-vis/dist/style.css";

const {
  HorizontalGridLines,
  VerticalGridLines,
  XAxis,
  XYPlot,
  YAxis,
  LineSeries
} = require("react-vis");

export type DataPoints = { x: number; y: number };

interface PriceGraphProps {
  data: DataPoints[];
  width: number;
  height: number;
  title: string;
  xDomain?: number[];
  yDomain?: number[];
}

const PriceGraph: React.FC<PriceGraphProps> = React.memo(
  ({ data, width, height, title }) => {
    return (
      <>
        <h2 className="centered">{title}</h2>
        <XYPlot width={width} height={height}>
          <XAxis
            title="Time (24 hr)"
            tickTotal={12}
            tickFormat={function tickFormat(unixTime: number) {
              const time = new Date(unixTime);
              const hours = time
                .getHours()
                .toString()
                .padStart(2, "0");
              const mins = time
                .getMinutes()
                .toString()
                .padStart(2, "0");
              return `${hours}-${mins}`;
            }}
          />
          <YAxis title="Price (AUD)" tickTotal={8} />
          <HorizontalGridLines />
          <VerticalGridLines />
          <LineSeries data={data} />
        </XYPlot>
      </>
    );
  }
);

export default PriceGraph;
