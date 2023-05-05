import {Outlet, Route, RouterProvider, createBrowserRouter, createRoutesFromElements} from 'react-router-dom'
import './App.css'
import User from './pages/user/user'
import Admin from './pages/admin/admin'
import Login from './pages/login/login'

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path='/' element={<Layout/>}>
        <Route path='login' element={<Login/>} />
        <Route path='user' element={<User/>} />
        <Route path='admin' element={<Admin/>} />
      </Route>
    )
  )
  return (
    <>
      <RouterProvider router={router} />  
    </>
  )
}

export default App

const Layout = () => {
  return (
    <>
      <div>NAV BAR</div>
      <main><Outlet /></main>
      <footer> Foo Ter</footer>
    </>
  );
};

{/* <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </> */}