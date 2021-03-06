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
      <p>??????/?????? ????????? ???</p>
      {data.map(data => (
        <h4 className="App-title">
          {data.coin1}:{data.coin2} -
          1{data.coin1} = {(data.balance2/data.balance1).toFixed(2)}
          {data.coin2}. ??????/????????? {data.coin2} ??????{'         '}
          <input type = "number" onChange = {amountChange}></input>{'         '}
          <button type="button" onClick = { () => window.confirm('?????????????????????????') ?
           deposit(data.coin2) : alert("??? ????????? ?????????????????????")} >????????????</button>{'         '}
          <button type="button" onClick = { () => window.confirm('????????? ?????????????????????????') ?
           farmFee(data.coin2) : alert("?????? ????????? ?????????????????????")} >????????????</button>{'         '}
          <button type="button" onClick = { () => window.confirm('?????????????????????????') ?
           withdraw(data.coin2) : alert("????????? ????????? ?????????????????????")} >????????????</button>       
        </h4>
      ))}
    </div>
    )
}