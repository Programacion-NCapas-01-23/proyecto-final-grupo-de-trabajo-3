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
import Charts from './pages/admin/Charts';
import Checkout from './pages/Checkout/Checkout';
import Landing from './Landing';
import OwnedTickets from './pages/OwnedTickets/OwnedTickets';

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <>
      <Route path="/" element={<Layout />}>

        <Route index element={<Home />} />
        
        <Route path="checkout" element={<Checkout />} />
        
        <Route path="user">
          <Route index element={<User />} />
          <Route path="owned-tickets" element={<OwnedTickets />} />
        
        </Route>
      </Route>
      
      <Route path="admin" element={<Charts />} />
      
      <Route path="login" element={<Login />} />
      
      <Route path="development" element={<Charts />} />
      
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
      <main className='pt-default-xl'>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};
