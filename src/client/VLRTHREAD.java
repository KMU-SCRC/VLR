package client;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
public class VLRTHREAD extends Thread{
	private Socket m_Socket;
	@Override
	public void run() {
		super.run();
		try {
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			while(true) {
				if(ChatClient.deviceList.isEmpty()) {
					sendWriter.println("VLRMSG:"+ChatClient.UserID+":LIST:NULL:END");
					sendWriter.flush();
					if(ChatClient.MSGVIEWSWITCH) {
						System.out.println("VLRMSG:"+ChatClient.UserID+"(나):LIST:NULL:END");
					}
				}else {
					sendWriter.println("VLRMSG:"+ChatClient.UserID+":LIST:START:END");
					sendWriter.flush();
					if(ChatClient.MSGVIEWSWITCH) {
						System.out.println("VLRMSG:"+ChatClient.UserID+"(나):LIST:START:END");
					}
					for(Map.Entry<String,Status>listUp:ChatClient.deviceList.entrySet()) {
						if((System.currentTimeMillis()-listUp.getValue().getTime())>ChatClient.LIVETIME) {
							listUp.getValue().setStatus("DISCONNECTED");
							if(ChatClient.MSGVIEWSWITCH) {
								System.out.println("VLR UPDATE : "+listUp.getKey()+" - TIME OUT DISCONNECTED");
							}
						}
						sendWriter.println("VLRMSG:"+ChatClient.UserID+":UPDATE:"+listUp.getKey()+":"+listUp.getValue().getChannel()+":"+listUp.getValue().getStatus()+":END");
						sendWriter.flush();
						if(ChatClient.MSGVIEWSWITCH) {
							System.out.println("VLRMSG:"+ChatClient.UserID+"(나):UPDATE:"+listUp.getKey()+":"+listUp.getValue().getChannel()+":"+listUp.getValue().getStatus()+":END");
						}
					}
					sendWriter.println("VLRMSG:"+ChatClient.UserID+":LIST:STOP:END");
					sendWriter.flush();
					if(ChatClient.MSGVIEWSWITCH) {
						System.out.println("VLRMSG:"+ChatClient.UserID+"(나):LIST:STOP:END");
					}
				}
				if(ChatClient.ENDSWITCH) {
					break;
				}
			}
			sendWriter.close();
			if(!m_Socket.isClosed()) {
				m_Socket.close();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void setSocket(Socket _socket)
	{
		m_Socket = _socket;
	}
}