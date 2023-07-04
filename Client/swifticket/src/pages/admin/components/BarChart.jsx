/* eslint-disable */
import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS } from 'chart.js/auto';

const BarChart = ({ dataSet, titles }) => {

  const chartData = {
    labels: titles,
    datasets: [
      {
        label: 'Tickets Sold',
        data: dataSet,
        backgroundColor: 'rgba(53, 162, 235, 0.5)',
      },
    ],
  };

  return <Bar data={chartData} />;
};

export default BarChart;
