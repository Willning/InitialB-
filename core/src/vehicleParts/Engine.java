package vehicleParts;

public class Engine {

	private int power;
	private int maxRPM;
	private int currentRPM;
	
	public int getPower(){
		return power;
	}
	public void rev(){
		if (currentRPM<=maxRPM){
			currentRPM+=power/10;
		}	
	}
	
	public int getcurrentRPM(){
		return currentRPM;
	}
			
	
}
