package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.fazecast.jSerialComm.*;
public class PortThread extends Thread{

	private Socket m_Socket;
//	private int portNum=0;
	private String portName="";
	
	@Override
	public void run() {
		super.run();
		try {
//			SerialPort comPort = SerialPort.getCommPorts()[portNum];
			SerialPort comPort = SerialPort.getCommPort(portName);
			comPort.setBaudRate(115200);
			comPort.setNumDataBits(8);
			comPort.setNumStopBits(1);
			comPort.openPort();
			comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(comPort.getInputStream()));
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			String sendString;
			String[] split;
			while(true)
			{
				sendString = tmpbuf.readLine();
				split=sendString.split(":");
				if(split.length >=2&&split[1].equalsIgnoreCase("ID")&&split[3].equalsIgnoreCase("CH")) {
					if(ChatClient.MSGVIEWSWITCH) {
						System.out.println(sendString);
					}
					if(split[4].equalsIgnoreCase("1")) {
						split[4]="INFRA RED";
					}else if(split[4].equalsIgnoreCase("2")) {
						split[4]="BLUE";
					}else if(split[4].equalsIgnoreCase("3")) {
						split[4]="RED";
					}else if(split[4].equalsIgnoreCase("4")) {
						split[4]="ACOUSTICS";
					}else if(split[4].equalsIgnoreCase("5")) {
						split[4]="BLUE AND RED";
					}else {
						split[4]="UNKNOWN CHANNEL";
					}
					if(ChatClient.deviceList.containsKey(split[2])) {
						Status status=ChatClient.deviceList.get(split[2]);
						status.setChannel(split[4]);
						status.setStatus("CONNECTED");
						status.setTime(System.currentTimeMillis());
						if(ChatClient.MSGVIEWSWITCH) {
							System.out.println("VLR UPDATE : "+split[2]+" - UPDATE");
						}
					}else {
						ChatClient.deviceList.put(split[2],new Status(split[2],split[4],"CONNECTED",System.currentTimeMillis()));
						if(ChatClient.MSGVIEWSWITCH) {
							System.out.println("VLR UPDATE : "+split[2]+" - CREATE");
						}
					}
					continue;
				}
				if(ChatClient.SENDSWITCH) {
					sendWriter.println("UHSDM ->> "+sendString);
					sendWriter.flush();
				}
				if(ChatClient.VIEWSWITCH) {
					System.out.println("UHSDM ->> "+sendString);
				}
				if(ChatClient.ENDSWITCH) {
					break;
				}
			}
			
			sendWriter.close();
			tmpbuf.close();
			comPort.closePort();
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

//	public void setPortNum(int portNum) {
//		this.portNum = portNum;
//	}
	
	public void setPortName(String portName) {
		this.portName = portName;
	}
}
