package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ChatClient {

	public static String UserID;
	public static ArrayList<PrintWriter> m_OutputList = new ArrayList<PrintWriter>();
	public static TreeMap<String,Status>deviceList=new TreeMap<String,Status>();
	public static boolean SENDSWITCH=false;
	public static boolean VIEWSWITCH=false;
	public static boolean OTHERVIEWSWITCH=false;
	public static boolean MSGVIEWSWITCH=false;
	public static boolean ENDSWITCH=false;
	public static boolean GATEWAYSWITCH=false;
	public static boolean AUTOSWITCH=false;
	public static boolean DELAYSWITCH=true;
	public static long LIVETIME=5000;
	public static long DELAY=1000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("접속할 주소를 입력해주세요. (예시 : 192.168.137.1)");
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			String address="0";
			address=tmpbuf.readLine();
			Socket c_socket = new Socket(address, 8888);
			
			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(c_socket);
			
			SendThread send_thread = new SendThread();
			send_thread.setSocket(c_socket);
			
			send_thread.start();
			rec_thread.start();
			
			ServerSocket s_socket = new ServerSocket(8889);
			HostSendThread hostSendThread=new HostSendThread();
			hostSendThread.start();
			AutoListThread autoListThread=new AutoListThread();
			autoListThread.start();
			while(true)
			{
				Socket a_socket = s_socket.accept();
				ClientManagerThread a_thread = new ClientManagerThread();
				a_thread.setSocket(a_socket);
				
				m_OutputList.add(new PrintWriter(a_socket.getOutputStream()));
				
				a_thread.start();
				GATEWAYSWITCH=true;
				if(ENDSWITCH) {
					break;
				}
			}
			s_socket.close();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {}
	}

}
