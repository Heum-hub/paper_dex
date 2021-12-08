import React, {useState, useEffect} from 'react';
import logo from './logo.jpg';
import './App.css';
import { Pool } from './Pool/Pool';
import { Wallet } from './Wallet/Wallet';
import { SignIn } from './Auth/SignIn';
import { SignUp } from './Auth/SignUp';
import { Block } from './Block/Block'
import { SwapParent } from './Swap/SwapParent';
import { NavBar } from './NavBar/NavBar';
import { HashRouter, Router, Route, Switch } from 'react-router-dom';

function App () {
  

  return (
      <div className="App"> 
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo"/>
          <br></br>
          <HashRouter>
            <Switch>
              <Route exact path = "/" component = {SignIn} />
              <Route exact path = "/signup" component = {SignUp} />              
              <Route exact path = "/dex" component = {NavBar} />
              <Route path = "/dex/pool" component =  {Pool} />
              <Route path = "/dex/wallet" component =  {Wallet} />
              <Route path = "/dex/swap" component =  {SwapParent} />
              <Route path = "/dex/block" component = {Block} />
            </Switch>
          </HashRouter>
        </header>
      </div>
    )

}

export default App;