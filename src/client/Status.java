package client;
public class Status {
	private String ID="Default ID";
	private String Channel="Default Channel";
	private String Status="Default Status";
	private long Time=System.currentTimeMillis();
	private long ContactTime=System.currentTimeMillis();
	public Status() {
		super();
	}
	public Status(String iD) {
		super();
		ID = iD;
	}
	public Status(String iD, String channel) {
		super();
		ID = iD;
		Channel = channel;
	}
	public Status(String iD, String channel, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
	}
	public Status(long time) {
		super();
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, long time) {
		super();
		ID = iD;
		Time = time;
		ContactTime=time;
	}
	public Status(long time, String iD) {
		super();
		ID = iD;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, long time) {
		super();
		ID = iD;
		Channel = channel;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, long time, String channel) {
		super();
		ID = iD;
		Channel = channel;
		Time = time;
		ContactTime=time;
	}
	public Status(long time, String iD, String channel) {
		super();
		ID = iD;
		Channel = channel;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, String status, long time) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, long time, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, long time, String channel, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(long time, String iD, String channel, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getChannel() {
		return Channel;
	}
	public void setChannel(String channel) {
		Channel = channel;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public long getTime() {
		return Time;
	}
	public void setTime(long time) {
		Time = time;
	}
	public long getContactTime() {
		return ContactTime;
	}
	public void setContactTime(long contactTime) {
		ContactTime = contactTime;
	}
}