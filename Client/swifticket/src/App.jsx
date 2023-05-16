import {
  Outlet,
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import './App.css';
import User from './pages/user/user';
import Admin from './pages/admin/admin';
import Login from './pages/Login/Login';
import { NavBar } from './components/NavBar';
import Home from './pages/home';
import Footer from './components/Footer';
import OneUser from './pages/user/id/oneUser';
import { Colors } from './pages/colors';

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/colors" element={<Colors></Colors>} />
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="user/one" element={<OneUser />} />
          <Route path="admin" element={<Admin />} />
        </Route>
        <Route path="user" element={<User />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  );
  return (
    <>
      <RouterProvider router={router} />
    </>
  );
}

export default App;

const Layout = () => {
  return (
    <>
      <NavBar />
      <main>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};

{
  /* <>
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
    </> */
}
