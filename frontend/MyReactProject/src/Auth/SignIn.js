import React, {useState, useEffect} from 'react';
import '../App.css';
import '../NavBar/Link.css';
import { Link } from "react-router-dom";

export function SignIn ({ history }) {

  // const token = localStorage.getItem('jwt-token')
  const [id, setId] = useState("");

  function handleChange1(event) {
    setId(event.target.value);
  }


  const [pw, setPw] = useState("");

  function handleChange2(event) {
    setPw(event.target.value);
  }
  
  function SignInSuccess() {
    
    fetch('authenticate', 
    {
      method: "POST",
      headers: {
          "Content-Type": "application/json"
      },
      body: JSON.stringify({
          username: id,
          password: pw
      })
    })
    .then( response => response.json())
    .then(
      data => {
      if (data.token) {
        localStorage.setItem('jwt-token', data.token)
        localStorage.setItem('user-id', id)
        alert("로그인 완료!")
        history.push("/dex")
      } else {
        alert("등록되지 않은 사용자입니다")
      }}
    );
  
  }  
  
  return (
    <div className="App">
      <h5>티맥스왑에 오신 것을 환영합니다!</h5>
      <h6>
        아이디{"                 "}
        <input onChange={handleChange1}/>
        <br></br>
        비밀번호{"                 "}
        <input type="password" onChange={handleChange2}/>
      </h6>
      <button type="button" onClick= { () => SignInSuccess() }>로그인</button>
      <br></br>
      <Link to = {'signup'} className="subnav_link">
        <button type="button">회원가입</button>
      </Link>
    </div>
    )

}