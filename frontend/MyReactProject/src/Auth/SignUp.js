import React, {useState, useEffect} from 'react';
import '../App.css';
import '../NavBar/Link.css';

export function SignUp ({ history }) {

  // const token = localStorage.getItem('jwt-token')
  const [id, setId] = useState("");

  function handleChange1(event) {
    setId(event.target.value);
  }


  const [pw, setPw] = useState("");

  function handleChange2(event) {
    setPw(event.target.value);
  }
  
  function SignUpSuccess() {
    
    fetch('/signup', 
    {
      method: "POST",
      headers: {
          "Content-Type": "application/json"
      },
      body: JSON.stringify({
          email: id,
          password: pw,
          auth: "ROLE_USER"
      })
    })
    .then( response => response.json())
    .then( response => {
      if (response.response == "success") {

        alert(response.message)


        const query = `?userId=${id}&userPw=${pw}`

        fetch('/dex/createWallet' + query, 
        {
          method: "POST"
        })
        .then( response => response.json())
        .then( response => {
          if (response.response == "success")  {
            alert(response.message)
            history.push("/")
        }})


    } else {
      alert(response.message)
    }
    });
  
  }  
  
  return (
    <div className="App">
      <h5>아이디와 비밀번호를 등록해주세요</h5>
      <h6>
        아이디{"                 "}
        <input onChange={handleChange1}/>
        <br></br>
        비밀번호{"                 "}
        <input type="password" onChange={handleChange2}/>
      </h6>
      <button type="button" onClick= { () => SignUpSuccess() }>회원가입</button>
    </div>
    )

}