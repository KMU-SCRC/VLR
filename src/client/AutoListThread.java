package client;

import java.text.SimpleDateFormat;
import java.util.Map;

public class AutoListThread extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		long time=System.currentTimeMillis();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		while(true) {
			if(ChatClient.ENDSWITCH) {
				break;
			}else if(ChatClient.AUTOSWITCH) {
				if(ChatClient.DELAYSWITCH) {
					if((System.currentTimeMillis()-time)<ChatClient.DELAY) {
						continue;
					}else {
						time=System.currentTimeMillis();
					}
				}
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
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
		}
	}
}