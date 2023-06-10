/* eslint-disable */
import React from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS } from 'chart.js/auto';

const LineChart = () => {
  const dataSet = [3, 5, 2, 1, 4];
  const titles = ['data 1', 'data 2', 'data 3', 'data 4', 'data 5'];
  const chartData = {
    labels: titles,
    datasets: [
      {
        label: 'DataSet 2',
        data: dataSet,
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
      },
    ],
  };

  return <Line data={chartData} />;
};

export default LineChart;
