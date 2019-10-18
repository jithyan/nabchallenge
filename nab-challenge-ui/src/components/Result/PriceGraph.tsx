/// <reference types="react-vis-types" />
import React from "react";

import {
  HorizontalGridLines,
  VerticalGridLines,
  XAxis,
  XYPlot,
  YAxis,
  MarkSeries,
  LineSeries
} from "react-vis";
import "react-vis/dist/style.css";

export type DataPoints = { x: number; y: number };

interface PriceGraphProps {
  data: DataPoints[];
  width: number;
  height: number;
  title: string;
  xDomain?: number[];
  yDomain?: number[];
}

const PriceGraph: React.FC<PriceGraphProps> = ({
  data,
  width,
  height,
  title
}) => {
  return (
    <>
      <h2 className="centered">{title}</h2>
      <XYPlot width={width} height={height} xDomain={[0, 6]} yDomain={[10, 20]}>
        <XAxis />
        <YAxis />
        <HorizontalGridLines />
        <VerticalGridLines />
        <LineSeries data={data} />
      </XYPlot>
    </>
  );
};

export default PriceGraph;
