package KartoffelKanaalPlugin.plugin.kartoffelsystems.PulserSystem;

import KartoffelKanaalPlugin.plugin.AttribSystem;
import KartoffelKanaalPlugin.plugin.IObjectCommandHandable;
import KartoffelKanaalPlugin.plugin.StoreTechnics;
import KartoffelKanaalPlugin.plugin.kartoffelsystems.PlayerSystem.Person;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class PNConditionOR extends PNCondition{

	protected PNCondition[] arr;

	protected PNConditionOR(PNCondition[] children, byte options, boolean invisible, int conditionID, PNTechCondition root){
		super(options, invisible, conditionID, root);
		this.arr = children;
	}
	
	protected PNConditionOR(PNCondition[] children, byte[] src){
		super(src);
		this.arr = children;
	}
	
	@Override
	protected byte getConditionType() {return 1;}
	
	protected boolean calculateValue(){
		if(arr == null)return ((this.options & 0x20) == 0x20);
		boolean atLeastOne = false;
		for(int i = 0; i < arr.length; i++){
			if(this.arr[i] == null || this.arr[i].isInvisible())continue;
			atLeastOne = true;
			if(arr[i].getConditionValue())return true;
		}
		if(atLeastOne){
			return false;
		}else{
			return ((this.options & 0x20) == 0x20);
		}
	}
	
	@Override
	protected int getEstimatedSize() {
		if(arr == null)return PNCondition.generalInfoLength();
		int l = PNCondition.generalInfoLength() + arr.length * 4;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] != null)l += arr[i].getEstimatedSize();
		}
		return l;
	}

	public static PNConditionOR loadFromBytes(byte[] src) {
		if(src == null || src.length < PNCondition.generalInfoLength() || src.length > 500000)return null;
		byte[][] c = StoreTechnics.loadArray(src, 100, 100000, PNCondition.generalInfoLength());
		PNCondition[] conditions = new PNCondition[c.length];
		for(int i = 0; i < c.length; i++){
			conditions[i] = PNCondition.loadFromBytes(src);
		}
		
		return new PNConditionOR(conditions, src);
	}

	@Override
	protected byte[] saveCondition(){
		byte[][] a = new byte[this.arr.length][];
		for(int i = 0; i < arr.length; i++){
			if(arr[i] == null){
				a[i] = new byte[0];
			}else{
				a[i] = arr[i].saveCondition();
				if(a[i] == null)a[i] = new byte[0];
			}
		}
		byte[] solution = StoreTechnics.saveArray(a, 100);
		if(solution == null || solution.length > 500000)solution = new byte[0];
		
		byte[] ans = new byte[solution.length + PNCondition.generalInfoLength()];
		
		this.saveGeneralInfo(ans);
		
		System.arraycopy(solution, 0, ans, PNCondition.generalInfoLength(), solution.length);
		
		return ans;
	}

	public static PNConditionOR createFromParams(String[] params, byte options, int ID, PNTechCondition root) throws Exception{
		if(params.length > 0)throw new Exception("Je kan enkel een lege PNConditionOR aanmaken. Children moeten achteraf toegevoegd worden. Gebruik dus geen parameters voor dit type.");
		return new PNConditionOR(new PNCondition[0], options, true, ID, root);
	}

	@Override
	public PNConditionOR createCopy(int ID, PNTechCondition root) throws Exception {
		PNCondition[] children = null;
		if(this.arr == null){
			children = new PNCondition[0];
		}else{
			children = new PNCondition[this.arr.length];
			for(int i = 0; i < this.arr.length; i++){
				if(this.arr[i] != null){
					children[i] = this.arr[i].createCopy(601, root);//TODO De Condition-ID moet dynamisch asigned worden
				}
			}
		}
		return new PNConditionOR(children, this.options, true, ID, root);
	}

	@Override
	public boolean handleObjectCommand(Person executor, CommandSender a, AttribSystem attribSys, String[] args) throws Exception {
		if(super.handleObjectCommand(executor, a, attribSys, args))return true;
		String commandLabel = args[0];
		if(commandLabel.equals("array")){
			this.arr = ConditionArrayFunctions.handleSubCommand(executor, a, attribSys, args, this, this.arr);
		}else{
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<String> autoCompleteObjectCommand(String[] args, ArrayList<String> a) throws Exception {
		a = super.autoCompleteObjectCommand(args, a);
		
		String commandLabel = args[0];
		if(commandLabel.equals("array")){
			a = ConditionArrayFunctions.autoCompleteSubCommand(args, a);
		}
		return a;
	}
	
	@Override
	public IObjectCommandHandable getSubObjectCH(String path) throws Exception {
		if(path.startsWith("#")){
			int arrIndex;
			try{
				arrIndex = Integer.parseInt(path.substring(1));
			}catch(NumberFormatException e){
				return null;
			}
			if(arrIndex < 0 || arrIndex >= this.arr.length)return null;
			return this.arr[arrIndex];
		}
		return null;
	}

	@Override
	public ArrayList<String> autoCompleteSubObjectCH(String s, ArrayList<String> a) throws Exception {
		return super.autoCompleteSubObjectCH(s, a);
	}
}
