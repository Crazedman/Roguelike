
public class Monster {

  public final int MAXINVENTORYSIZE=30;	//maximum inventory size
	public final int INVENTORYSLOTS=6;		//number of places to wear equipment
	
	public Monster(){
		name = null;
		hitPoints[0]=0;
		hitPoints[1]=0;
		baseDamage = 0;
	}
	
	public Monster(String name){
		this.name = name;
		hitPoints[0]=0;
		hitPoints[1]=0;
		baseDamage = 0;
	}

	//toString methods
	
	public String showHitPoints(){
		return hitPoints[0]+"/"+hitPoints[1];
	}
	
	public String showEquipment(){
		boolean noEquipment=true;
		String retVal="Equipped items:"+"\n";
		
		for(int i=0;i<INVENTORYSLOTS;i++){
			if(equippedItems[i]!=null){		//if an item is equipped in the slot
				noEquipment=false;
				String slotName="";
				switch(i){					//maybe this switch statement should have its own method
				case(0):
					slotName="Head";
					break;
				case(1):
					slotName="Chest";
					break;
				case(2):
					slotName="Left Hand";
					break;
				case(3):
					slotName="Right Hand";
					break;
				case(4):
					slotName="Pants";
					break;
				case(5):
					slotName="Boots";
					break;
				default:
					break;
				}
				retVal+=(slotName+": "+equippedItems[i].toString()+"\n");
			}
		}
		
		if(noEquipment){
			retVal+="none";
		}
		
		return retVal;
		
	}
	
	//movement methods
	//public void Move(direction)
	
	
	//damage-related functions
	
	public int determineDamage(){	//will be more complicated later
		int retVal = baseDamage;
		if(equippedItems[3]!=null){
			retVal+=equippedItems[3].getPower(1);
		}
		return retVal;
	}
	
	public void attack(Monster target){		//attacks a target
		int damage = determineDamage();
		target.takeDamage(damage);
	}
	
	public void takeDamage(int damage){		//takes damage	
		for(int i = 0;i<INVENTORYSLOTS;i++){
			if(equippedItems[i]!=null){
			damage-=equippedItems[i].getPower(0);
			}
		}
		
		if(damage<0){		//if there is more than enough armor to mitigate the damage, no damage is dealt.
			damage = 0;
		}
		
		hitPoints[0]-=damage;
		if(hitPoints[0]<=0){
			die();
		}
	}
	
	public void restoreHealth(int health){	//restores health
		hitPoints[0]+=health;
		if(hitPoints[0]>maxHitPoints()){
			fillHitPoints();
		}
	}
	
	public void die(){				//develop this more		(drop items, can no longer perform actions, object gets deleted, more specific message appears, etc.)	
		System.out.println(name+" died.");
	}
	
	
	
	//hit point getters and setters	(Note: currently allows negative numbers. Change this if it causes problems.
	
	public int[] getHitPoints() {
		return hitPoints;
	}
	
	public int maxHitPoints() {
		return hitPoints[1];
	}
	
	
	public int currentHp(){
		return hitPoints[0];
	}
	
	public void setHitPoints(int hitPoints) {		//only meant for initially creating a monster with full health.
		this.hitPoints[0] = hitPoints;
		this.hitPoints[1] = hitPoints;
	}
	
	public void fillHitPoints(){					//restore to full HP
		hitPoints[0]=hitPoints[1];
	}
	
	public void setMaxHitPoints(int newMax){
		hitPoints[1]=newMax;
	}
	
	//damage getters and setters
	
	public int getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
	
	//Item related functions
	
		//TODO: obtainItem(try to add an item to inventory, but only succeed if there is room.)
		
		public void useItem(int index){
			if(inventory.getItem(index)!=null){
				inventory.getItem(index).use(this);
				if(inventory.getItem(index).consumable==true){
					inventory.removeItem(index);
				}
			}
			
		}
		
		public void equipItem(Equipment item){				//TODO: make it so program can stop player from equipping non-equipment items.
			
			int index = item.wornIndex;
			
			if(item.equipped==false){						//case for equipping an item
			
				item.equip();
				
				if(equippedItems[index]==null){	//checks to see if an item is already worn there
					equippedItems[index]=item;
				}
				else{											//if an item is already worn there, do this
					unequipItem(item.wornIndex);				//this step unequips the current item and equips the chosen item in its place.
					
					if(item.wornIndex==3 && item.twoHanded){	//if the item is a two-handed weapon, unequip the left-hand item.
						unequipItem(2);
					}
					
					else if(item.wornIndex==2	&&	equippedItems[3].twoHanded){	//if equipping a left-hand item while holding a two-handed weapon, unequip the weapon.
						unequipItem(2);
						equippedItems[2]=item;
						
						unequipItem(3);
					}
					
					equippedItems[index]=item;
				}
				
			}
			
			else{											//case for unequipping an item
				unequipItem(item.wornIndex);
			}
		}
		
		public void unequipItem(int index){		//unequip an item
			if(equippedItems[index]!=null){
				equippedItems[index].equip();		
				equippedItems[index]=null;
			}
		}
		
		//name getters and setters
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	protected String name;
	protected int[] hitPoints=new int[2];	//two-int array, with first as current and second and maximum
	protected int baseDamage;
	
	protected Inventory inventory = new Inventory();
	protected Equipment[] equippedItems = new Equipment[INVENTORYSLOTS];

	
}
