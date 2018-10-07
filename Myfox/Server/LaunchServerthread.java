package Myfox.Server;

public class LaunchServerthread implements Runnable{
	public String name;
	public Object synobj;
	public LaunchServerthread(String name,Object obj){
		this.name = name;
		this.synobj = obj;
	}
	@Override
	public void run(){
		
	}
}