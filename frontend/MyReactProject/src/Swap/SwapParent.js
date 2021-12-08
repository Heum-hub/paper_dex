import React from 'react';
import { SwapSellCoinName } from './SwapSellCoinName';
import { SwapBuyCoinName } from './SwapBuyCoinName';
import { SwapSellAmount } from './SwapSellAmount';
import { SwapBuyAmount } from './SwapBuyAmount';
import '../App.css';

export class SwapParent extends React.Component {

  constructor(props) {
    
    super(props);
    this.state = { coin1: 'BTC', coin2: 'BTC' , coin3: '', coin4: ''};
    this.selectCoin1 = this.selectCoin1.bind(this);
    this.selectCoin2 = this.selectCoin2.bind(this);
    this.selectCoin3 = this.selectCoin3.bind(this);
    this.selectCoin4 = this.selectCoin4.bind(this);

  }

  selectCoin1(sellCoin) {
    this.setState({
      coin1: sellCoin
    });
  }

  selectCoin2(buyCoin) {
    this.setState({
      coin2: buyCoin
    });
  }

  selectCoin3(inputCoin) {
    this.setState({
      coin3: inputCoin
    });
  }

  selectCoin4(outputCoin) {
    this.setState({
      coin4: outputCoin
    });
  }


  render() {

    return (
       <div>
         <SwapSellCoinName coin1 = {this.state.coin1} onChange = {this.selectCoin1} />
         <SwapBuyCoinName coin2 = {this.state.coin2} onChange = {this.selectCoin2} />
         <SwapSellAmount coin1 = {this.state.coin1} coin2 = {this.state.coin2} 
         coin3 = {this.state.coin3} coin4 = {this.state.coin4}
         onChange = {this.selectCoin3}
         />
         <SwapBuyAmount coin1 = {this.state.coin1} coin2 = {this.state.coin2} 
         coin3 = {this.state.coin3} coin4 = {this.state.coin4}
         onChange = {this.selectCoin4}
         />
       </div>


    );

  }


}