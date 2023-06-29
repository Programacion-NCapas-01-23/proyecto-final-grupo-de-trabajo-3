import React, { lazy, Suspense } from 'react';
import {
  Outlet,
  Route,
  RouterProvider,
  createBrowserRouter,
  createRoutesFromElements,
} from 'react-router-dom';
import './App.css';
const User = lazy(() => import('./pages/user/user'));
// import User from './pages/user/user';
const Login = lazy(() => import('./pages/Login/login'));
// import Login from './pages/Login/login';
const NavBar = lazy(() => import('./components/NavBar'));
// import NavBar from './components/NavBar';
const Home = lazy(() => import('./pages/home'));
// import Home from './pages/home';
const Footer = lazy(() => import('./components/Footer'));
// import Footer from './components/Footer';
const Admin = lazy(() => import('./pages/admin/admin'));
// import Admin from './pages/admin/admin';
const Checkout = lazy(() => import('./pages/Checkout/Checkout'));
// import Checkout from './pages/Checkout/Checkout';
const Landing = lazy(() => import('./Landing'));
// import Landing from './Landing';
const Charts = lazy(() => import('./pages/admin/Charts'));
// import Charts from './pages/admin/Charts';
const Catalogs = lazy(() => import('./pages/admin/Catalogs'));
// import Catalogs from './pages/admin/Catalogs';
const Tables = lazy(() => import('./pages/admin/Tables'));
// import Tables from './pages/admin/Tables';
const OwnedTickets = lazy(() => import('./pages/OwnedTickets/OwnedTickets'));
// import OwnedTickets from './pages/OwnedTickets/OwnedTickets';
const OneEvent = lazy(() => import('./pages/OneEvent/OneEvent'));
// import OneEvent from './pages/OneEvent/OneEvent';
const ReceiveQR = lazy(() => import('./pages/ReceiveQR/ReceiveQR'));
// import ReceiveQR from './pages/ReceiveQR/ReceiveQR';
const SendQR = lazy(() => import('./pages/OneEvent/SendQR/SendQR'));
// import SendQR from './pages/OneEvent/SendQR/SendQR';
const CreateEvent = lazy(() => import('./pages/admin/CreateEvent/CreateEvent'));
// import CreateEvent from './pages/admin/CreateEvent/CreateEvent';
const AllEvents = lazy(() => import('./pages/admin/AllEvents'));
// import AllEvents from './pages/admin/AllEvents';
const ModifyCatalogs = lazy(() =>
  import('./pages/admin/ModifyCatalogs/ModifyCatalogs')
);
// import ModifyCatalogs from './pages/admin/ModifyCatalogs/ModifyCatalogs';
const ScanQr = lazy(() => import('./pages/ScanQr/ScanQr'));
// import ScanQr from './pages/ScanQr/ScanQr';
import { useRecoilValue } from 'recoil';
import { tokenState } from './state/atoms/tokenState';

function App() {
  const token = useRecoilValue(tokenState);

  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />

          <Route path="checkout" element={<Checkout />} />

          <Route path="receive-qr" element={<ReceiveQR />} />
          <Route path="scan-qr" element={<ScanQr />} />

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
        <Route path="admin" element={<Admin />}>
          <Route index element={<Charts />} />
          <Route path="catalogs" element={<Catalogs />} />
          <Route path="modify-catalogs" element={<ModifyCatalogs />} />
          <Route path="tables" element={<Tables />} />
          <Route path="create-event" element={<CreateEvent />} />
          <Route path="all-events" element={<AllEvents />} />
        </Route>
        <Route path="development" element={<Admin />} />
        <Route path="error" element={<Landing />} />
        <Route path="*" element={<Home />} />
      </>
    )
  );

  const routerLogin = createBrowserRouter(
    createRoutesFromElements(
      <>
        <Route path="login" element={<Login />} />
        <Route path="*" element={<Login />} />
      </>
    )
  );

  return (
    <>
      <Suspense
        fallback={
          <div className="flex h-screen w-screen justify-center items-center">
            Loading...
          </div>
        }
      >
        {token ? (
          <RouterProvider router={router} />
        ) : (
          <RouterProvider router={routerLogin} />
        )}
      </Suspense>
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
