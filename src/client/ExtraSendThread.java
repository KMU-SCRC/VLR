package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ExtraSendThread extends Thread{

	private Socket m_Socket;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			sendWriter.println("REGUIHOEDNRHUIOERHNOSUUIGBVIERGBIVIDURRBGYIFVBI:"+ChatClient.UserID);
			sendWriter.flush();
			
			while(true)
			{
				if(ChatClient.ENDSWITCH)
				{
					break;
				}
				else if(!ChatClient.deviceList.isEmpty()){
					for(Map.Entry<String,Status>listUp:ChatClient.deviceList.entrySet()) {
						if(listUp.getValue().getStatus().equalsIgnoreCase("DISCONNECTED")){
							sendWriter.println("VLRMSG:"+ChatClient.UserID+":REQ:"+listUp.getKey()+":END");
							sendWriter.flush();
							if(ChatClient.MSGVIEWSWITCH) {
								System.out.println("VLRMSG:"+ChatClient.UserID+"(나):REQ:"+listUp.getKey()+":END");
							}
						}
					}
				}
			}
			
			sendWriter.close();
			if(!m_Socket.isClosed()) {
				m_Socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}

}
