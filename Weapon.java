
public class Weapon extends Equipment{

  public Weapon(){
		name=null;
		setStatBoosts();
		wornIndex=3;
	}
	
	public Weapon(String name){
		this.name=name;
		setStatBoosts();
		wornIndex=3;	
	}
	
	public Weapon(String name, int power){
		this.name=name;
		setPower(power);
		wornIndex=3;	
	}
	
	public int getPower(){
		return statBoosts[1];
	}
	
	public void setPower(int power) {
		statBoosts[1]=power;
	}

	public boolean twoHanded = false;

	
}
