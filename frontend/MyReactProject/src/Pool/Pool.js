import React, {useState, useEffect} from 'react';
import '../App.css';

export function Pool () {

  const token = localStorage.getItem('jwt-token')

  const [data, setData] = useState([]);
  
  useEffect(() => {
    fetch('/dex/pool',
    {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
    .then(response => response.json())
    .then(data => {
      setData(data);
    });
  }, [])

  let amountToDeposit = 0;
  let amountToWithdraw = 0;

  const amountChange = (event) => {

    amountToDeposit = event.target.value;
    amountToWithdraw = event.target.value

  }

  const deposit = (coinName) => {

    const query = `?coinName=${coinName}&coinAmount=${amountToDeposit}&userId=${localStorage.getItem('user-id')}`

    fetch('/dex/addLiquidity' + query,
    {
      method: "PUT",
      headers: {
          'Authorization': 'Bearer ' + token  
      }
    })
    .then(response => response.json())
    .then(response => {
      if (response.response == "success") {
        alert(response.message)
      } else {
        alert(response.message)
      }
    
    });

  }

  const farmFee = (coinName) => {

    const query = `?coinName=${coinName}&userId=${localStorage.getItem('user-id')}`

    fetch('/dex/farmFee' + query,
    {
      method: "PUT",
      headers: {
          'Authorization': 'Bearer ' + token  
      }
    })
    .then(response => response.json())
    .then(response => {
      if (response.response == "success") {
        alert(response.message)
      } else {
        alert(response.message)
      }
    
    });


  }

  const withdraw = (coinName)  => {

    const query = `?coinName=${coinName}&coinAmount=${amountToWithdraw}&userId=${localStorage.getItem('user-id')}`

    fetch('/dex/withdrawLiquidity' + query,
    {
      method: "PUT",
      headers: {
          'Authorization': 'Bearer ' + token  
      }
    })
    .then(response => response.json())
    .then(response => {
      if (response.response == "success") {
        alert(response.message)
      } else {
        alert(response.message)
      }
    
    });


  }


  return (
    <div className="App">
      <p>거래/예치 가능한 풀</p>
      {data.map(data => (
        <h4 className="App-title">
          {data.coin1}:{data.coin2} -
          1{data.coin1} = {(data.balance2/data.balance1).toFixed(2)}
          {data.coin2}. 예치/회수할 {data.coin2} 수량{'         '}
          <input type = "number" onChange = {amountChange}></input>{'         '}
          <button type="button" onClick = { () => window.confirm('예치하시겠습니까?') ?
           deposit(data.coin2) : alert("풀 예치가 취소되었습니다")} >예치하기</button>{'         '}
          <button type="button" onClick = { () => window.confirm('이자를 수령하시겠습니까?') ?
           farmFee(data.coin2) : alert("이자 수령이 취소되었습니다")} >이자받기</button>{'         '}
          <button type="button" onClick = { () => window.confirm('회수하시겠습니까?') ?
           withdraw(data.coin2) : alert("유동성 회수가 취소되었습니다")} >회수하기</button>       
        </h4>
      ))}
    </div>
    )
}