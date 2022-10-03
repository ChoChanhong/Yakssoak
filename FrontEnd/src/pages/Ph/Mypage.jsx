import { React, useEffect, useState } from "react";
import PhNavbar from "../../Components/PhNavbar";

export function Mypage() {
  const [account, setAccount] = useState("");

  const getAccount = async () => {
    try {
      if (window.ethereum) {
        const accounts = await window.ethereum.request({
          method: "eth_requestAccounts",
        });

        setAccount(accounts[0]);
      } else {
        alert("Install Metamask!");
      }
    } catch (error) {
      console.error(error);
    }
  };

  // const navigate = useNavigate();
  // const {
  //     connector,
  //     library,
  //     chainId,
  //     account,
  //     active,
  //     error,
  //     activate,
  //     deactivate,
  //   } = useWeb3React();

  // useEffect(()=>{
  //     if(active){activate(injected)}
  //     else{navigate('/login')}
  // },[]);

  return (
    <div>
      <PhNavbar />
      <h1>약사 마이페이지</h1>
      <div>
        <div>{account}</div>
        <button onClick={getAccount}>dd</button>
        {/* <p>Account: {account}</p>
                <p>ChainId: {chainId}</p> */}
      </div>
    </div>
  );
}