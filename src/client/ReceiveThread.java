package client;

import java.io.BufferedReader;
import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
//import java.net.SocketException;

public class ReceiveThread extends Thread{

	private Socket m_Socket;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));
//			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			String receiveString;
			String[] split;
			
			while(true)
			{
				receiveString = tmpbuf.readLine();
//				split = receiveString.split("HLRMSG:");
//				if(split.length >= 2)
//				{
//					split=split[1].split(":");
//					if(split[0].equals(ChatClient.UserID)&&split[1].equalsIgnoreCase("DEL")&&ChatClient.deviceList.containsKey(split[2])&&ChatClient.deviceList.get(split[2]).getStatus().equalsIgnoreCase("DISCONNECTED")) {
//						ChatClient.deviceList.remove(split[2]);
//						sendWriter.println("VLRMSG:"+ChatClient.UserID+":DEL:"+split[2]);
//						sendWriter.flush();
//					}else if(split[0].equals(ChatClient.UserID)&&split[1].equalsIgnoreCase("DEL")&&!ChatClient.deviceList.containsKey(split[2])) {
//						sendWriter.println("VLRMSG:"+ChatClient.UserID+":DEL:"+split[2]);
//						sendWriter.flush();
//					}
//					continue;
//				}
				split = receiveString.split(">");
				if(split.length >= 2 && split[0].equals(ChatClient.UserID))
				{
					continue;
				}
				split=receiveString.split("UHSDM ->>");
				if(split.length<2||ChatClient.OTHERVIEWSWITCH) {
					System.out.println(receiveString);
				}
				if(ChatClient.ENDSWITCH) {
					break;
				}
			}
			tmpbuf.close();
			if(!m_Socket.isClosed()) {
				m_Socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}

}
