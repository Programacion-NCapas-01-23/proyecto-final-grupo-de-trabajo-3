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
import Login from './pages/Login/login';
import { NavBar } from './components/NavBar';
import Home from './pages/home';
import Footer from './components/Footer';
import OneUser from './pages/user/id/oneUser';
import Charts from './pages/admin/Charts';
import OneEvent from './pages/OneEvent/OneEvent';
import Checkout from './pages/Checkout/Checkout';
import Landing from './Landing';

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="user/one" element={<OneUser />} />
          <Route path="admin" element={<Admin />} />
          <Route path="checkout" element={<Checkout />} />
        </Route>
        <Route path="user" element={<User />} />
        <Route path="/login" element={<Login />} />
        <Route path="/development" element={<Charts />} />
        <Route path="/error" element={<Landing />} />
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
