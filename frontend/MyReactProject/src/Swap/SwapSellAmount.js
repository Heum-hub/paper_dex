import React, {useState, useEffect} from 'react';
import '../App.css';

export function SwapSellAmount (props) {

  const token = localStorage.getItem('jwt-token')

  const [data, setData] = useState("");
  
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
  },[])

  function handleChange3(event) {
    const coin3 = event.target.value;
    props.onChange(coin3);
  }

  return (
    <div className="App">
      <h4>
        매도할 {props.coin1} 수량:{'         '}
        <input id= 'coin3' placeholder={props.coin4/10} onChange={handleChange3}/>
      </h4>
    </div>
    )
}