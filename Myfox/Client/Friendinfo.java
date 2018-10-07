package Myfox.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import Myfox.ProtoBuf.MessageProbuf;

public class Friendinfo{
	int id;
	String name;
	String ip;
	String state;
	Friendinfo(){
		this.id = 0;
	}
	Friendinfo(int id,String name,String ip,String state){
		this.id = id;
		this.name = new String(name);
		this.ip = new String(ip);
		this.state = new String(state);
	}
}