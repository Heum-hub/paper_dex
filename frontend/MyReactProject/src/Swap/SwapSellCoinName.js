import React from 'react';

export function SwapSellCoinName (props) {
  
  function handleChange1(event) {
    const coin1 = event.target.value;
    props.onChange(coin1);
  }
  
  
  return (
      <div>
        <h4>
          매도할 코인 {props.coin1}{'        '}
          <select id="coin1" onChange = {handleChange1}>
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