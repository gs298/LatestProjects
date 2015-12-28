package businesslogic;
public class AliveCounter{

//Class to keep track of thread count on server side
private int number;
AliveCounter(){
    number=0;
    }


public synchronized int getNumber(){
return number;
}

public synchronized void setNumber(int inNumber){
	number=inNumber;
}
}