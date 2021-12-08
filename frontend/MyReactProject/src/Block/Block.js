import React, {useState, useEffect} from 'react';
import '../App.css';
import { SHA256 } from './SHA256';

export function Block () {

  const token = localStorage.getItem('jwt-token')

  // 전체 블록 조회
  const [data, setData] = useState([]);
  
  useEffect(() => {

    fetch('/dex/block',
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

  let rootHash = "";

  // 특정 블록 조회
  const [block, setBlock] = useState("");

  let blockNumber = "";

  const blockNumberChange = (event) => {

    blockNumber = event.target.value;

    blockQuery(blockNumber);

  }

  const blockQuery = (blockNumber) => {

    fetch(`/dex/block/${blockNumber}`,
    {
      headers: {
        'Authorization': 'Bearer ' + token
      }
    })
    .then(response => response.json())
    .then(block => {
      setBlock(block);
    });
    
  }


  
  return (
    <div className="App">
        <p>
          {
          (data.forEach(data => (
            rootHash += data.hash
            )))
          }
          블록체인 루트해시: 0x{SHA256(rootHash)} 
        </p>
        <p>전체 블록 수: {data.length}</p>
        <p>
          블록 넘버로 조회하기:{'         '}
          <input type = "number" onChange = {blockNumberChange} />
        </p>
        <br></br>
        <p>블록 해시:{'         '}{block.hash}</p>
        <p>블록 생성 시간:{'         '}{block.timeStamp}</p>
        <p>출금 주소:{'         '}{block.fromAddress}</p>
        <p>입금 주소:{'         '}{block.toAddress}</p>
        <p>전송된 코인:{'         '}{block.coinName}</p>
        <p>전송된 코인 수량:{'         '}{block.coinAmount}</p>
    </div>
    )
}