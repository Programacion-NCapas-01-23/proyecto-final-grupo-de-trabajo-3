import {
  Outlet,
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import './App.css';
import User from './pages/user/user';
import Login from './pages/Login/login';
import { NavBar } from './components/NavBar';
import Home from './pages/home';
import Footer from './components/Footer';
import Admin from './pages/Admin/Admin';
import Checkout from './pages/Checkout/Checkout';
import Landing from './Landing';
import OwnedTickets from './pages/OwnedTickets/OwnedTickets';
import OneEvent from './pages/OneEvent/OneEvent';
import ReceiveQR from './pages/ReceiveQR/ReceiveQR';
import SendQR from './pages/OneEvent/SendQR/SendQR';

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />

          <Route path="checkout" element={<Checkout />} />

          <Route path="receive-qr" element={<ReceiveQR />} />
          <Route path="scan-qr" element={<ReceiveQR />} />

          <Route path="user">
            <Route index element={<User />} />
            <Route path="owned-tickets" element={<OwnedTickets />} />
          </Route>

          <Route path="event">
            <Route index element={<Landing />} />
            <Route path=":eventId">
              <Route index element={<OneEvent />} />
              <Route path="send-qr" element={<SendQR />} />
              <Route path="buy" element={<Landing />} />{' '}
              {/* CREATE A BUYING VARIANT OF ONE ELEMENT */}
            </Route>
          </Route>
        </Route>

        <Route path="admin" element={<Admin />} />

        <Route path="login" element={<Login />} />

        <Route path="development" element={<Admin />} />

        <Route path="error" element={<Landing />} />
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
      <main className="pt-[3.5rem]">
        <Outlet />
      </main>
      <Footer />
    </>
  );
};
