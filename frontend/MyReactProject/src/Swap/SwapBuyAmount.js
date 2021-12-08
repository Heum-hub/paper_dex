import React, {useState, useEffect} from 'react';
import '../App.css';

export function SwapBuyAmount (props) {

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

  function handleChange4(event) {
    const coin4 = event.target.value;
    props.onChange(coin4);
  }
  
  function SwapSuccess(props) {

    const query = `?sellCoinName=${props.coin1}&buyCoinName=${props.coin2}&sellCoinAmount=${props.coin3}&userId=${localStorage.getItem('user-id')}`
  
    fetch('/dex/swap/result' + query, 
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
      <h4>
        매수할 {props.coin2} 수량:{'         '}
        <input id='coin4' placeholder={props.coin3*10} onChange={handleChange4}/>
      </h4>
      <button type="button" onClick= { () => window.confirm('스왑하시겠습니까?') ?
      SwapSuccess(props) : alert('스왑이 취소되었습니다!') }>스왑하기</button>
    </div>
    )
}

