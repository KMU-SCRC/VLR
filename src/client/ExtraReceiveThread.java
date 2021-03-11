package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class ExtraReceiveThread extends Thread{

	private Socket m_Socket;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			String receiveString;
			String[] split;
			
			while(true)
			{
				receiveString = tmpbuf.readLine();
				split = receiveString.split(":");
				if(ChatClient.ENDSWITCH) {
					break;
				}
				else if(split.length==5&&split[0].equalsIgnoreCase("VLRMSG")&&split[4].equalsIgnoreCase("END"))
				{
					if(ChatClient.MSGVIEWSWITCH) {
						System.out.println(receiveString);
					}
					if(split[2].equalsIgnoreCase("REQ")&&ChatClient.deviceList.containsKey(split[3])&&ChatClient.deviceList.get(split[3]).getStatus().equalsIgnoreCase("CONNECTED")) {
						sendWriter.println("VLRMSG:"+ChatClient.UserID+":RES:"+split[3]+":END");
						sendWriter.flush();
						if(ChatClient.MSGVIEWSWITCH) {
							System.out.println("VLRMSG:"+ChatClient.UserID+"(나):RES:"+split[3]+":END");
						}
					}else if(split[2].equalsIgnoreCase("RES")&&ChatClient.deviceList.containsKey(split[3])&&ChatClient.deviceList.get(split[3]).getStatus().equalsIgnoreCase("DISCONNECTED")) {
						ChatClient.deviceList.remove(split[3]);
						if(ChatClient.MSGVIEWSWITCH) {
							System.out.println("VLR UPDATE : "+split[3]+" - DELETE");
						}
					}
					continue;
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
