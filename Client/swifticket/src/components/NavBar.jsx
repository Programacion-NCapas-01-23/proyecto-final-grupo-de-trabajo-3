import React from 'react'
import { Link } from 'react-router-dom'

export const NavBar = () => {
  return (
    <nav>
        <Link to='/'> HOME </Link>
        <Link to='/admin'> ADMIN </Link>
        <Link to='/user'> USER </Link>
    </nav>
  )
}