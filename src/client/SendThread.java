package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Map;

public class SendThread extends Thread{

	private Socket m_Socket;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			sendWriter.println("REGUIHOEDNRHUIOERHNOSUUIGBVIERGBIVIDURRBGYIFVBI");
			sendWriter.flush();
			String sendString;
			long time=5000;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//			System.out.println("장비 식별 ID를 입력해주세요.");
			ChatClient.UserID = tmpbuf.readLine();
			
			sendWriter.println(ChatClient.UserID);
			sendWriter.flush();
			
			boolean startswitch=true;
			
			while(true)
			{
				sendString = tmpbuf.readLine();

				if(sendString.equalsIgnoreCase("exit"))
				{
					ChatClient.ENDSWITCH=true;
					break;
				}
				else if(sendString.equalsIgnoreCase("help")) {
					System.out.println("==================");
					System.out.println("도움말");
					System.out.println("==================");
					System.out.println("exit - 종료");
					System.out.println("start - 유아트 통신 시작 and 유아트에서 온 메세지를 서버(HQ)로 전송 시작");
					System.out.println("view - 유아트 통신 기록 보기");
					System.out.println("noview - 유아트 통신 기록 안 보기");
					System.out.println("otherview - 다른 장비의 서버(HQ)에 올려진 유아트 통신 기록 보기");
					System.out.println("nootherview - 다른 장비의 서버(HQ)에 올려진 유아트 통신 기록 안 보기");
					System.out.println("msgview - 장비간의 메세지 보기");
					System.out.println("nomsgview - 장비간의 메세지 안 보기");
					System.out.println("send - 유아트에서 온 메세지를 서버(HQ)로 전송");
					System.out.println("stop - 유아트에서 온 메세지를 서버(HQ)로 전송 안 하기");
					System.out.println("set - 장비 목록 유지 시간 설정");
					System.out.println("add - 네트워크 추가");
					System.out.println("list - 통신권내에 있는 장비 목록 (VLR)");
					System.out.println("auto - 자동으로 리스트 보이기");
					System.out.println("noauto - 자동으로 리스트 보이지 않기");
					System.out.println("delay - 자동 리스트 주기 설정");
					System.out.println("nodelay - 자동 리스트 주기 끄기");
					System.out.println("del - 장비 목록 수동 삭제");
					System.out.println("put - 장비 목록 수동 추가");
					System.out.println("==================");
					continue;
				}
				else if(sendString.equalsIgnoreCase("start")&&startswitch) {
//					System.out.println("연결된 포트 순번을 입력해주세요.");
					System.out.println("연결된 포트를 입력해주세요.");
					sendString = tmpbuf.readLine();
					PortThread portThread=new PortThread();
					portThread.setSocket(m_Socket);
//					portThread.setPortNum(Integer.parseInt(sendString));
					portThread.setPortName(sendString);
					ChatClient.SENDSWITCH=true;
					portThread.start();
					startswitch=false;
					VLRTHREAD vlrthread=new VLRTHREAD();
					vlrthread.setSocket(m_Socket);
					vlrthread.start();
					System.out.println("유아트 통신 시작");
					continue;
				}
				else if(sendString.equalsIgnoreCase("view")) {
					ChatClient.VIEWSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("noview")) {
					ChatClient.VIEWSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("otherview")) {
					ChatClient.OTHERVIEWSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("nootherview")) {
					ChatClient.OTHERVIEWSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("msgview")) {
					ChatClient.MSGVIEWSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("nomsgview")) {
					ChatClient.MSGVIEWSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("send")) {
					ChatClient.SENDSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("stop")) {
					ChatClient.SENDSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("set")) {
					System.out.println("장비 목록 유지 시간을 설정해주세요. 기본설정은 5초(5000)입니다.");
					sendString = tmpbuf.readLine();
					try {
						time=Long.parseLong(sendString);
						ChatClient.LIVETIME=time;
						System.out.println("설정이 완료되었습니다.");
					}catch (Exception e) {
						System.out.println("숫자만 입력해주세요.");
					}
					continue;
				}
				else if(sendString.equalsIgnoreCase("add")) {
					System.out.println("추가로 접속할 주소를 입력해주세요. (예시 : 192.168.137.1)");
					sendString=tmpbuf.readLine();
					Socket c_socket = new Socket(sendString, 8889);
					ExtraReceiveThread rec_thread = new ExtraReceiveThread();
					rec_thread.setSocket(c_socket);
					ExtraSendThread send_thread = new ExtraSendThread();
					send_thread.setSocket(c_socket);
					send_thread.start();
					rec_thread.start();
					System.out.println("접속완료");
					continue;
				}
				else if(sendString.equalsIgnoreCase("list")) {
					System.out.println("==================");
					System.out.println("기록시간 : "+sdf.format(System.currentTimeMillis()));
					System.out.println("==================");
					System.out.println("VLR("+ChatClient.UserID+") 장비목록");
					System.out.println("==================");
					if(ChatClient.deviceList.isEmpty()) {
						System.out.println("없음");
					}else {
						for(Map.Entry<String,Status>listUp:ChatClient.deviceList.entrySet()) {
							System.out.println("##################");
							System.out.println("ID : "+listUp.getKey());
							System.out.println("채널 : "+listUp.getValue().getChannel());
							System.out.println("상태 : "+listUp.getValue().getStatus());
							System.out.println("가입시간 : "+sdf.format(listUp.getValue().getContactTime()));
							System.out.println("갱신시간 : "+sdf.format(listUp.getValue().getTime()));
							System.out.println("##################");
						}
					}
					System.out.println("==================");
					continue;
				}
				else if(sendString.equalsIgnoreCase("auto")) {
					ChatClient.AUTOSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("noauto")) {
					ChatClient.AUTOSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("delay")) {
					System.out.println("자동 리스트 주기 시간을 설정해주세요. 기본설정은 1초(1000)입니다.");
					sendString = tmpbuf.readLine();
					try {
						time=Long.parseLong(sendString);
						ChatClient.DELAY=time;
						System.out.println("설정이 완료되었습니다.");
					}catch (Exception e) {
						System.out.println("숫자만 입력해주세요.");
					}
					ChatClient.DELAYSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("nodelay")) {
					ChatClient.DELAYSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("del")) {
					System.out.println("삭제할 장비를 입력해주세요. 전체 삭제를 원할시 all 입력");
					sendString = tmpbuf.readLine();
					if(sendString.equalsIgnoreCase("all")) {
						if(ChatClient.deviceList.isEmpty()) {
							System.out.println("삭제할 장비가 없습니다.");
						}else {
							ChatClient.deviceList.clear();
							System.out.println("정상적으로 전체 삭제되었습니다.");
						}
					}else if(ChatClient.deviceList.containsKey(sendString)) {
						ChatClient.deviceList.remove(sendString);
						System.out.println("정상적으로 삭제되었습니다.");
					}else {
						System.out.println("해당 장비가 없습니다.");
					}
					continue;
				}
				else if(sendString.equalsIgnoreCase("put")) {
					if(ChatClient.deviceList.containsKey("D1")) {
						ChatClient.deviceList.get("D1").setStatus("CONNECTED");
						ChatClient.deviceList.get("D1").setTime(System.currentTimeMillis());
						System.out.println("장비갱신");
					}else {
						ChatClient.deviceList.put("D1",new Status("D1","BLUE","CONNECTED",System.currentTimeMillis()));
						System.out.println("장비추가");
					}
					continue;
				}
				else {
					sendWriter.println(sendString);
					sendWriter.flush();
				}
			}
			
			sendWriter.close();
			tmpbuf.close();
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
