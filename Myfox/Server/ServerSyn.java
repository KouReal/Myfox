package Myfox.Server;

public class ServerSyn{
	public ServerSyn(){
		synobj = new Object();
		synobj1 = new Object();
		launchserverstate = true;
		connectdbstate = true;
		port = 18872;
	}
	public Object synobj;
	public Object synobj1;
	public boolean launchserverstate;
	public boolean connectdbstate;
	public int port;
}