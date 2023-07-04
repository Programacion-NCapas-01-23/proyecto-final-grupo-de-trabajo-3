/* eslint-disable */
import React from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS } from 'chart.js/auto';

const LineChart = ( { dataSet, titles } ) => {
  const chartData = {
    labels: titles,
    datasets: [
      {
        label: 'Hours',
        data: dataSet,
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
    ],
  };

  return <Line data={chartData} />;
};

export default LineChart;
