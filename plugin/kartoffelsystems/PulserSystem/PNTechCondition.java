package KartoffelKanaalPlugin.plugin.kartoffelsystems.PulserSystem;

import KartoffelKanaalPlugin.plugin.IObjectCommandHandable;

import java.util.ArrayList;

public class PNTechCondition extends PNTech{
	private PNCondition root;
	
	protected PNTechCondition(PNCondition root, boolean invisible, int ID, PulserNotifStandard notificationBase){
		super(invisible, ID, notificationBase);
		this.root = root;
		if(this.root != null)this.root.root = this;
	}
	
	protected PNTechCondition(PNCondition root, byte[] src){
		super(src);
		this.root = root;
		if(this.root != null)this.root.root = this;
	}
	
	@Override
	public byte getTechType() {return 2;}

	@Override
	public String getTypeName(){
		return "TechCondition";
	}

	protected boolean preventMessage(){
		if(this.root == null)return false;
		//System.out.println("De value berekenen van de rootcondition...");
		boolean b = this.root.getConditionValue();
		//System.out.println("De value is " + b);
		return !b;
	}
	
	protected PNCondition getBaseCondition(){
		return this.root;
	}

	protected void setBaseCondition(PNCondition c){
		this.root = c;
		if(this.root != null){
			this.root.root = this;
		}
	}

	@Override
	public int getEstimatedSize() {
		return PNTech.generalInfoLength() + root.getEstimatedSize();
	}

	protected static PNTechCondition loadFromBytes(byte[] src){
		if(src == null || src.length < PNTech.generalInfoLength())return null;
		
		byte[] conditionData = new byte[src.length - PNTech.generalInfoLength()];
		
		System.arraycopy(src, PNTech.generalInfoLength(), conditionData, 0, conditionData.length);
		
		return new PNTechCondition(PNCondition.loadFromBytes(conditionData), src);
	}
	
	protected byte[] saveTech(){
		if(root == null)return new byte[0];
		
		byte[] conditionArr = root.saveCondition();
		
		byte[] ans = new byte[conditionArr.length + PNTech.generalInfoLength()];
		
		this.saveGeneralInfo(ans);
		
		System.arraycopy(conditionArr, 0, ans, PNTech.generalInfoLength(), conditionArr.length);
		
		return ans;
	}
	
	public static PNTechCondition createFromParams(String[] params, int ID, PulserNotifStandard notificationBase) throws Exception {
		throw new Exception("Functie nog niet beschikbaar");
	}

	@Override
	public PNTech createCopy(int ID, PulserNotifStandard notificationBase) throws Exception{
		throw new Exception("Functie nog niet beschikbaar");
	}

	@Override
	public IObjectCommandHandable getSubObjectCH(String path) throws Exception {
		{
		IObjectCommandHandable c = super.getSubObjectCH(path);
		if(c != null)return c;
		}
		
		if(path.equals("root") || path.equals("base") || path.equals("condition"))return this.root;
		return null;
	}

	@Override
	public ArrayList<String> autoCompleteSubObjectCH(String s, ArrayList<String> a) throws Exception {
		a = super.autoCompleteSubObjectCH(s, a);		
		s = s.toLowerCase();
		if("root".startsWith(s))a.add("root");
		if("base".startsWith(s))a.add("base");
		if("condition".startsWith(s))a.add("condition");
		return a;
	}
}
