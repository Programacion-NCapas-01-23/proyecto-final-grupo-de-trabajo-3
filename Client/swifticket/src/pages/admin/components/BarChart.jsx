/* eslint-disable */
import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS } from 'chart.js/auto';

const BarChart = () => {
  const dataSet = [1, 5, 3, 2];
  const titles = ['data 1', 'data 2', 'data 3', 'data 4'];
  const chartData = {
    labels: titles,
    datasets: [
      {
        label: 'Dataset 1',
        data: dataSet,
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
    ],
  };

  return <Bar data={chartData} />;
};

export default BarChart;
