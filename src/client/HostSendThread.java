package client;

import java.util.Map;

public class HostSendThread extends Thread{
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			while(true)
			{
				if(ChatClient.ENDSWITCH)
				{
					break;
				}
				else if(!ChatClient.GATEWAYSWITCH) {
					continue;
				}
				else if(!ChatClient.deviceList.isEmpty()){
					for(Map.Entry<String,Status>listUp:ChatClient.deviceList.entrySet()) {
						if(listUp.getValue().getStatus().equalsIgnoreCase("DISCONNECTED")){
							for(int i = 0; i < ChatClient.m_OutputList.size(); ++i)
							{
								ChatClient.m_OutputList.get(i).println("VLRMSG:"+ChatClient.UserID+":REQ:"+listUp.getKey()+":END");
								ChatClient.m_OutputList.get(i).flush();
								if(ChatClient.MSGVIEWSWITCH) {
									System.out.println("VLRMSG:"+ChatClient.UserID+"(나):REQ:"+listUp.getKey()+":END");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
