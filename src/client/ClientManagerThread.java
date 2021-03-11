package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManagerThread extends Thread{

	private Socket m_socket;
	private String m_ID;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("접속시도감지");
		try {
			BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			PrintWriter sendWriter=new PrintWriter(m_socket.getOutputStream());
			String text;
			String[] split;
			do {
				text = tmpbuffer.readLine();
				split=text.split(":");
				if(split.length==2&&split[0].equals("REGUIHOEDNRHUIOERHNOSUUIGBVIERGBIVIDURRBGYIFVBI")) {
					m_ID = split[1];
				}else {
					System.out.println(m_socket.getInetAddress()+"가 인증되지 않은 프로그램으로 접속을 시도하였습니다.");
					sendWriter.println("인증된 프로그램으로 접속을 시도해주세요.");
					sendWriter.flush();
					break;
				}
				
				System.out.println(m_ID + "(" + m_socket.getInetAddress() + ")" + " - 추가 연결 시작");
//				for(int i = 0; i < ChatClient.m_OutputList.size(); ++i)
//				{
//					ChatClient.m_OutputList.get(i).println(m_ID + " - 추가 연결 시작");
//					ChatClient.m_OutputList.get(i).flush();
//				}
				
				while(true)
				{
					if(ChatClient.ENDSWITCH) {
						break;
					}
					text = tmpbuffer.readLine();
					if(text == null)
					{
						System.out.println(m_ID + " - 추가 연결 종료");
//						for(int i = 0; i < ChatClient.m_OutputList.size(); ++i)
//						{
//							ChatClient.m_OutputList.get(i).println(m_ID + " - 추가 연결 종료");
//							ChatClient.m_OutputList.get(i).flush();
//						}
						break;
					}
					split = text.split(":");
					if(split.length==5&&split[0].equalsIgnoreCase("VLRMSG")&&split[4].equalsIgnoreCase("END"))
					{
						if(ChatClient.MSGVIEWSWITCH) {
							System.out.println(text);
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
			}while(false);
			sendWriter.println("접속을 종료합니다.");
			sendWriter.flush();
			sendWriter.close();
			tmpbuffer.close();
			ChatClient.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
			if(!m_socket.isClosed()) {
				m_socket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_socket = _socket;
	}
}
