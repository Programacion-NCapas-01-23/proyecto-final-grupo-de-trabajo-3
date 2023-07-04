import React from 'react'
import ReactDOM from 'react-dom'
import App from './App.jsx'
import './index.css'
import { RecoilRoot } from 'recoil'

ReactDOM.render(
  <React.StrictMode>
    <RecoilRoot> 
      <App />
    </RecoilRoot>
  </React.StrictMode>,
  document.getElementById('root')
)
