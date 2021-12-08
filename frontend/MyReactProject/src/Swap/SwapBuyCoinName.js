import React from 'react';

export function SwapBuyCoinName (props) {

  
  function handleChange2(event) {
    const coin2 = event.target.value;
    props.onChange(coin2);
  }
  

  return (
      <div>
        <h4>
          매수할 코인 {props.coin2}{'        '}       
          <select id="coin2" onChange = {handleChange2}>
            <option value="BTC">
              BTC
            </option>

            <option value="ETH">
              ETH
            </option>

            <option value="ADA">
              ADA
            </option>

            <option value="EOS">
              EOS
            </option>

            <option value="OMG">
              OMG
            </option>

            <option value="DAI">
              DAI
            </option>
          </select>
        </h4>     
      </div>
    );
}