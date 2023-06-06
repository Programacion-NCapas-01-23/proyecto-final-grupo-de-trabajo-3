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
import { Colors } from './pages/colors';
import Cards from './pages/Cards';
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
        <Route path="/development" element={<OneEvent />} />
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
      <main className='pt-default-xl'>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};
