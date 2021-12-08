import React, {useState, useEffect} from 'react';
import '../App.css';

export function Wallet () {

  const token = localStorage.getItem('jwt-token')

  const [data, setData] = useState("");
  
  useEffect(() => {

    const query = "?userId=" + localStorage.getItem('user-id')

    fetch('/dex/wallet' + query,
    {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
    .then(response => response.json())
    .then(data => {
      setData(data);
    });
  },[])
  
  return (
    <div className="App">
      <p>내 지갑 정보</p>
      <h4 className="App-title">지갑주소: {data.walletAddress}</h4>
      <h4 className="App-title">BTC: {(data.btcQuantity/1).toFixed(2)}</h4>
      <h4 className="App-title">ETH: {(data.ethQuantity/1).toFixed(2)}</h4>
      <h4 className="App-title">ADA: {(data.adaQuantity/1).toFixed(2)}</h4>
      <h4 className="App-title">EOS: {(data.eosQuantity/1).toFixed(2)}</h4>
      <h4 className="App-title">OMG: {(data.omgQuantity/1).toFixed(2)}</h4>
      <h4 className="App-title">DAI: {(data.daiQuantity/1).toFixed(2)}</h4>      
    </div>
    )
}