import React, { useEffect } from 'react';
import ReactDOM from 'react-dom/client';
import { Web3ReactProvider } from '@web3-react/core';
import { Web3Provider } from '@ethersproject/providers';
import { injected } from '../../lib/Connectors';
import { useWeb3React } from '@web3-react/core';
import { useNavigate } from 'react-router-dom';
import DocNavbar from "../../Components/DocNavbar"

export function Main(){
    const navigate = useNavigate();
    const {
        chainId,
        account,
        active,
        error,
        activate,
        deactivate,
      } = useWeb3React();
    
    useEffect(()=>{
        if(active){activate(injected)}
        else{navigate('/doc/login')}        
    },[]);

    return(
        <div>
            <DocNavbar/>
            <div>님, 안녕하세요!</div>
            <div>
                <div>
                    의사 정보
                    <div>
                        <div>병원명</div>
                        <div>요양기관번호</div>
                        <div>대표원장성명</div>
                        <div>면허번호</div>
                        <div>대표이메일</div>
                        <div>주소</div>
                        <div>연락처</div>
                    </div>
                </div>
                <div>
                    지갑 정보
                    <div>
                        지갑 주소 : {account}
                    </div>
                </div>
            </div>
        </div>
    )
}