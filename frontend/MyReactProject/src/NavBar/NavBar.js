import React from 'react';
import '../App.css';
import './Link.css'
import { Link } from "react-router-dom";

export function NavBar () {
  
    const pages = ['pool', 'wallet', 'swap', 'block'];
    const navLinks = pages.map(page => {
      return (
        <Link to = {'dex/' + page} className="subnav_link">
          <h4 className="App-title">
            { page == 'pool' ? '풀 보기 ' :  
            ( page == 'wallet' ? '내 지갑 보기' : 
            ( page == 'swap' ? '스왑하기' : '블록 보기' ))}
          </h4>         
        </Link>
      )
    });     

    return (
      <div>
        <p>티맥스왑에 오신 걸 환영합니다!</p>
        <nav>{navLinks}</nav>
        <button type="button" onClick = { () => 
        {localStorage.removeItem("jwt-token")
        localStorage.removeItem("user-id")
        alert("로그아웃 완료!")}}
        >로그아웃</button>
      </div>
    );
}