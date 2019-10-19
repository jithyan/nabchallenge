import React from "react";

import "react-vis/dist/style.css";

const {
  HorizontalGridLines,
  VerticalGridLines,
  XAxis,
  XYPlot,
  YAxis,
  MarkSeries,
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
        <XYPlot width="800" height="400">
          <XAxis
            title="Time (24 hr)"
            tickFormat={function tickFormat(unixTime: number) {
              const time = new Date(unixTime);
              console.log(time.toTimeString());
              return `${time.getHours()}-${time.getMinutes()}`;
            }}
          />
          <YAxis title="Price (AUD)" />
          <HorizontalGridLines />
          <VerticalGridLines />
          <LineSeries data={data} />
        </XYPlot>
      </>
    );
  }
);

export default PriceGraph;
