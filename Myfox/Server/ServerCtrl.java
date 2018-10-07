package Myfox.Server;

public class ServerCtrl{
	public static int bindport = 18872;
	public static void main(String[] args){
		//launch server
		System.out.println("launching server-------------------------");

		ServerSyn syn0 = new ServerSyn();
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					ManWeb.bind(syn0);
				}
				catch(Exception e){
					synchronized(syn0.synobj){
						syn0.launchserverstate = false;
						e.printStackTrace();
						syn0.synobj.notify();
					}
				}
			}
		}).start();
		
		synchronized(syn0.synobj){	
			try{
				syn0.synobj.wait();
			}
			catch (InterruptedException e) {     
                e.printStackTrace();     
            }   
		}

		if(syn0.launchserverstate == true){
			System.out.println("server is running-------------------------");
		}
		else{
			System.out.println("server failed to launch-------------------------");
		}

		//connect to database
		System.out.println("connecting to database-------------------------");

		ServerSyn syn1 = new ServerSyn();
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					ManDB.connect(syn1);
				}
				catch(Exception e){
					synchronized(syn1.synobj1){
						syn1.connectdbstate = false;
						syn1.synobj1.notify();
						e.printStackTrace();
					}
				}
			}
		}).start();
		synchronized(syn1.synobj1){
			try{
				syn1.synobj1.wait();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		if(syn1.connectdbstate == true){
			System.out.println("connect to database successfully------------------------");
		}
		else{
			System.out.println("failed to connect to database-----------------------");
		}
	}
}