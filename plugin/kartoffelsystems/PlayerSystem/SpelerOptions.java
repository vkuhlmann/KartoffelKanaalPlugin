package KartoffelKanaalPlugin.plugin.kartoffelsystems.PlayerSystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;import org.bukkit.command.CommandSender;import org.bukkit.command.ConsoleCommandSender;import org.bukkit.entity.Player;import org.bukkit.inventory.ItemStack;

import KartoffelKanaalPlugin.plugin.DataFieldShort;
import KartoffelKanaalPlugin.plugin.Main;

public class SpelerOptions {//S = static aan of uit, D = dynamic aan of uit//S0 = static uit, S1 = static aan//D0 = dynamic uit, D1 = dynamic aan/*Legende * TIME = TIMEout, DDIA = Daily DIAmond, DOBE = Donatierank + Bedrockblocks * PLOA = PLayerLoadcount, PL = PermissionLevelDataFields:	RANK,TIME,--DDIA--,--DDIA--,DOBE,PLOA,PLOA,(byte)PL00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,Options + Permissions:	OPTION__00,OPTION__01,DYNAMIC_02,STATIC__03,DYNAMIC_04,STATIC__05,DYNAMIC_06,STATIC__07,	DYNAMIC_08,STATIC__09,DYNAMIC_10,STATIC__11,DYNAMIC_12,STATIC__13,DYNAMIC_14,STATIC__15 */	/*---Predefined SpelerOptions---*/ //---------------------------------------------------------------------------------------
private static final byte[] defaultOptions = new byte[]{                            	   1,  90,   0x00,   0x00,0x0A,0x00,0x00,(byte)0x10,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,       	(byte)0x28,(byte)0x00,(byte)0x00,         0,(byte)0x00,         0,(byte)0x00,         0,	(byte)0x00,         0,(byte)0x00,         0,(byte)0x00,         0,(byte)0x00,         0
};//---------------------------------------------------------------------------------------
public static final SpelerOptions CONSOLE = new StaticSpelerOptions(new byte[]{	 126,   0,   0x00,   0x00,0x00,0x00,0x00,(byte)0xE0,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,	(byte)0x00,(byte)0x00,(byte)0xC0,(byte)0xFF,(byte)0x70,(byte)0xFF,(byte)0xF6,(byte)0xFF,	(byte)0x00,(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0xE0,(byte)0xFF,(byte)0x00,(byte)0xFF});//---------------------------------------------------------------------------------------public static final SpelerOptions DEV = new StaticSpelerOptions(new byte[]{	127,  90,   0x00,   0x00,0x00,(byte)0x00,(byte)0x00,(byte) 0xF0,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,	(byte)0x00,(byte)0x00,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,	(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF});//---------------------------------------------------------------------------------------public static final SpelerOptions BLOCKEXECUTOR = new StaticSpelerOptions(new byte[]{//blockexecutor mag niemand perms of ranks geven	 71,   0,   0x00,   0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,		(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0xF6,(byte)0xFF,	(byte)0x00,(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x00,(byte)0xFF,(byte)0x00,(byte)0xFF});/*---Switch Library---*///Voorwaarden://boolean isCorrectArrayLength()return (optionAdresses.length >= optionNames.length) && (permissionAdresses.length >= permissionNames.length)//boolean isCorrectAdress(byte adress)return correctAdress = ((adress & 0x80) == 0);//boolean isCorrectOptionAdress(byte adress)return isCorrectAdress(adress) && ((adress & 0x0E) == 0x00);//boolean isCorrectPermissionAdress(byte adress)return isCorrectAdress(adress) && ((adress & 0x0E) != 0x00);//boolean isCorrectName(String name)return (name != null && name.length >= 2);//-------------public static final String[] optionNames = new String[]{	"pulser.vulnerable", "prefix.personal", "prefix.rank"};public static final byte[] optionAdresses = new byte[]{	0x50, 0x40, 0x30};public static final String[] permissionNames = new String[]{
	"autoantilag.regelen",	"pulser.notificaties.control", "donateur.bedrockinv", "donateur.fly",
			
	"arena.hoofdpermission", "arena.host.aanmaken", "arena.host.aanmaken.adv",
	"arena.host.aanmaken.adv.arenawapens", "arena.gevecht.deelnemen",
	"arena.gevecht.pause.timeout", "arena.gevecht.pause.infinite",
	
	"notifreceivement.change.mask1", "notifreceivement.change.mask2", "notifreceivement.change.all",
	"perms.essentials.protect.exempt"
};
public static final byte[] permissionAdresses = new byte[]{
	0x72,	0x74, 0x64, 0x54,
			
	0x76, 0x66, 0x56, 0x46, 0x36, 0x26, 0x16,
		
	0x78, 0x68, 0x58,
	0x48
};/*---General Variables---*/	//private static long sinceDate = 0;/*---Local Variables---*/	private byte[] data;	protected Person parent;	private long latestChange = 0;
	protected long latestSaved = 0;/*---DataExplaining---*/
	//#0
	//1 byte: rank
	
	//#1: Arena-timeout
	//1 byte: max pauzeertijd
	
	//#2 + 3: ~leeg~ --Laatst gekregen dag van DailyDiamond--
	
	//#4:
	//4 bits: Donatierank (0 = niet gedonneerd)
	//4 bits: Bedrockblocks tegoed
	
	//#5 + 6: LoadCount

	//#7:	//4 bits: PermissionLevel	//4 bits: leeg		//#8	//#9	//#10	//#11	//#12	//#13	//#14	//#15			//------------
	//#16: Options
	//1 bit [0x70]: leeg
	//1 bit [0x60]: leeg
	//1 bit [0x50]: Vulnerable voor pulser
	//1 bit [0x40]: Personal Prefix		//1 bit [0x30]: Rank Prefix	//1 bit [0x20]: leeg	//1 bit [0x10]: leeg	//1 bit [0x00]: leeg		//------------	//#17: Options	//1 bit [0x71]: leeg	//1 bit [0x61]: leeg	//1 bit [0x51]: leeg	//1 bit [0x41]: leeg		//1 bit [0x31]: leeg	//1 bit [0x21]: leeg	//1 bit [0x11]: leeg	//1 bit [0x01]: leeg
		//------------
	//#18 (+ #19): Operator Permissions
	//1 bit [0x72]: Kan autoantilag regelen	//1 bit [0x62]: leeg	//1 bit [0x52]: leeg	//1 bit [0x42]: leeg		//1 bit [0x32]: leeg	//1 bit [0x22]: leeg	//1 bit [0x12]: leeg	//1 bit [0x02]: leeg
		//------------
	//#20 (+ #21): Donateur Permissions
	//1 bit [0x74]: leeg
	//1 bit [0x64]: Kan bedrock block opeisen
	//1 bit [0x54]: Mag donateur-fly gebruiken
	//1 bit [0x44]: leeg		//1 bit [0x34]: leeg	//1 bit [0x24]: leeg	//1 bit [0x14]: leeg	//1 bit [0x04]: leeg
		//------------
	//#22 ( + #23): Arena
	//1 bit [0x76]: Hoofdpermission (kan anderen ook permissions geven)
	//1 bit [0x66]: Een host aanmaken
	//1 bit [0x56]: Een geavanceerde host aanmaken
	//1 bit [0x46]: Een geavanceerde host aanmaken met arenawapens	
	//1 bit [0x36]: Mag deelnemen
	//1 bit [0x26]: Mag gevecht pauseren/hervatten zolang als de Arena-timeout
	//1 bit [0x16]: Mag gevecht pauseren/hervatten ongelimiteerd
	//1 bit [0x06]: leeg
	
	//------------
	//#24 (+ #25): Non-operator permissions
	//1 bit [0x78]: Mag PulserNotifReceivement veranderen volgens de eerste mask
	//1 bit [0x68]: Mag PulserNotifReceivement veranderen volgens de tweede mask
	//1 bit [0x58]: Mag alle PulserNotifReceivements veranderen
	//1 bit [0x48]: Mag Essentials use- en place-banlist bypassen. Mag dus lava, TnT,.. plaatsen...
		
	//1 bit [0x38]: leeg
	//1 bit [0x28]: leeg
	//1 bit [0x18]: leeg
	//1 bit [0x08]: leeg
		//#26 - 29: leeg		//#30 + 31:	//1 bit: voorbehouden voor "niet bestaand"	//7 bits: leeg/*---Constructor---*/
	public SpelerOptions(byte[] a){
		/*if(sinceDate == 0){
			Calendar c = Calendar.getInstance();
			c.set(2014, 5, 1, 0, 0, 0);
			sinceDate = c.getTimeInMillis();
		}*/		data = (a == null || a.length != 32)?defaultOptions.clone():a;		if(this.getOpStatus() < 1 && this.getRank() >= 70)this.setRank((byte) 10);		short s = (short) (data[5] << 8 | data[6]);		if(s != Short.MAX_VALUE){			++s;		}		data[5] = (byte) (s >>> 8);		data[6] = (byte) (s & 0x00FF);
	}
	/*---General Functions---*/
	public static byte getOptionAdress(String s){
		//0x7F = onbekend
		if(s == null || s.length() < 2)return 0x7F;		if(s.charAt(0) == '#'){			int index;			try{				index = Integer.parseInt(s.substring(1));			}catch(Exception e){				return 0x7F;			}			if(index < 0 || index >= optionAdresses.length)return 0x7F;			return optionAdresses[index];		}		if(s.charAt(0) == '*'){			byte adress;			try{				adress = Byte.parseByte(s.substring(1));			}catch(Exception e){				return 0x7F;			}			if((adress & 0x80) == 0x80)return 0x7F;						if((adress & 0x0E) != 0x00)return 0x7F;			//if((adress & 0x0F) < 0 || (adress & 0x0F) > 1)return 0x7F;						return adress;		}
		//adress: 3 bits: bit index, 4 bits: byte index
		for(int i = 0; i < optionNames.length; i++){
			if(s.equals(optionNames[i]))return optionAdresses[i];
		}
		return 0x7F;
	}	public static byte getPermissionAdress(String s){		//0x7F = onbekend		if(s == null || s.length() < 2)return 0x7F;		if(s.charAt(0) == '#'){			int index;			try{				index = Integer.parseInt(s.substring(1));			}catch(Exception e){				return 0x7F;			}			if(index < 0 || index >= permissionAdresses.length)return 0x7F;			return permissionAdresses[index];		}		if(s.charAt(0) == '*'){			byte adress;			try{				adress = Byte.parseByte(s.substring(1));			}catch(Exception e){				return 0x7F;			}			if((adress & 0x80) == 0x80)return 0x7F;						if((adress & 0x0F) < 2)return 0x7F;			adress &= 0x7E;//voorkomt mogelijke exploits als voor permissions een if-functie voor eliminatie wordt uitgevoerd waarbij geen rekening gehouden wordt met oneven getallen (de static/dynamic gedeeltes) 						return adress;		}		//adress: 3 bits: bit index, 4 bits: byte index		for(int i = 0; i < permissionNames.length; i++){			if(s.equals(permissionNames[i]))return permissionAdresses[i];		}		return 0x7F;	}	public static String getAdressName(byte b, boolean clearification){		for(int i = 0; i < optionNames.length; ++i){//de optionNames array kan kleiner zijn dan optionAdresses array			if(optionAdresses[i] == b){				if(clearification){					return "(Option) " + optionNames[i];				}else{					return optionNames[i];				}			}		}		for(int i = 0; i < permissionNames.length; ++i){//de permissionNames array kan kleiner zijn dan permissionAdresses array			if(permissionAdresses[i] == b){				if(clearification){					return "(Permission) " + permissionNames[i];				}else{					return permissionNames[i];				}			}		}		return "?option/permission[" + b + "]";	}/*---Functions Part 1: User Input---*/	public void setRank(byte r, SpelerOptions executor, CommandSender a, boolean notifyAffected){		if(a == null)return;		if(!(a instanceof Player ||  a instanceof ConsoleCommandSender)){			a.sendMessage("�4Ranks veranderen is enkel beschikbaar voor spelers en de console");			return;		}		if(executor == null){			try{				a.sendMessage("�4Error: Missing commandexecutor");			}catch(Throwable e){}			return;		}		if(executor.getOpStatus() < 2){			if(executor.getRank() >= 70)executor.setRank((byte) 10);			a.sendMessage("�4Je hebt geen machtiging om dit commando te gebruiken");			return;		}		if(r == -128){a.sendMessage("�4Onbekende rank");return;}				byte exerank = executor.getRealRank();		boolean self = executor == this;				if(r < this.getDonatorRank()){			a.sendMessage("�4Een rank kan nooit lager zijn dan de donateurrank");			a.sendMessage("�4Verander de donateurrank van iemand met �c/donateurrank <player> <nieuwe rank>");			a.sendMessage("�4De nieuwe rank zal de donateurrank zijn (" + Rank.getRankName(this.getDonatorRank()) + ")");			r = this.getDonatorRank();		}				if(r >= 20 && r < 70 && r != this.getDonatorRank()){			a.sendMessage("�4Donateurranken moeten veranderd worden met �c/donateurrank");			return;		}				if(r >= 100 && executor.getOpStatus() < 3){			a.sendMessage("�4Een Owner moeten benoemd worden via de console");			return;		}				if(self){			//moet wel op zijn omdat de executor op is						if(r > exerank){				a.sendMessage("�4Je kan jezelf geen hogere rank geven dan waar je recht op hebt");				return;			}		}else{			if(r > exerank){				a.sendMessage("�4Je kan iemand niet een hogere rank geven dan wat jij hebt");				return;			}			if(this.getOpStatus() > 1){				if(exerank < 100){					a.sendMessage("�4Niet spelen met de ranks van andere ops!");					return;				}			}else if(this.getOpStatus() < 1){				if(r >= 70){					a.sendMessage("�4De nieuwe rank vereist op");					return;				}			}		}		this.setRank(r);		String display = Rank.getRankName(this.getRank());				if(self){			a.sendMessage("�eJe hebt je eigen rank veranderd naar " + display);		}else{			if(this.parent != null){				a.sendMessage("�eDe rank van " + (this.parent.name == null?"iemand":this.parent.name) + " is nu " + display);				if(notifyAffected)this.parent.sendMessage("�eJe rank is veranderd naar " + display);			}else{				a.sendMessage("�eDe rank is nu " + display);			}
					}		//if(!self && this.parent != null && this.parent.player != null)this.parent.player.sendMessage("Je rank is veranderd naar " + Rank.getDisplayRankName(this.getRank()));	}
	public void setDonatorRank(byte r, SpelerOptions executor, CommandSender a, boolean notifyAffected){		if(a == null)return;		if(executor == null){			try{				a.sendMessage("�4Error: Missing commandexecutor");			}catch(Exception e){}			return;		}		if(!(a instanceof Player || a instanceof ConsoleCommandSender)){			a.sendMessage("�4Donateur-ranken veranderen is enkel beschikbaar voor spelers en de console");			return;		}
		if(executor.getRank() < 100 || executor.getOpStatus() < 2/*Moet absoluut zeker zijn dat de persoon Operator is*/){
			a.sendMessage("�4Enkel Owners hebben toegang tot dit commando");
			return;
		}

		if(r >= 20 && r < 70){
			data[4] &= 0x0F;
			if(r >= 35){
				data[4] |= 0x40;
			}else if(r >= 30){
				data[4] |= 0x30;
			}else if(r >= 25){
				data[4] |= 0x20;
			}else{
				data[4] |= 0x10;
			}
		}else if(r == -127 || r == 0) /*als er "geen" is opgegeven*/{
			data[4] &= 0x0F;
			if(this.getRank() < 70)this.setRank((byte) 10);
		}else{
			a.sendMessage("�4Onbekende donateurrank");
		}
		this.latestChange = System.currentTimeMillis();
		if(this.getDonatorRank() > this.getRank() || (this.getDonatorRank() < this.getRank() && this.getRank() >= 20 && this.getRank() < 70)){
			this.setRank(this.getDonatorRank());
		}
		
		if(executor == this){
			a.sendMessage("�eJe donateurrank is veranderd naar " + Rank.getRankDisplay(this.getDonatorRank()));
		}else{
			if(this.parent != null && this.parent.name != null){
				a.sendMessage("�eDe donateurrank van " + (this.parent.name == null?"iemand":this.parent.name) + " is veranderd naar " + Rank.getRankName(this.getDonatorRank()));
				if(notifyAffected)this.parent.sendMessage("�eJe donateurrank is veranderd naar " + Rank.getRankName(this.getDonatorRank()));
			}else{
				a.sendMessage("�eDe donateurrank is veranderd naar " + Rank.getRankName(this.getDonatorRank()));
			}
		}
	}	public void giveDailyDiamonds(Player p){		if(p == null || Main.sm == null)return;		if(getAmountDailyDiamonds() > 0){			if(DailyDiamondReady()){
				int s = p.getInventory().firstEmpty();
				if(s == -1){
					p.sendMessage("�4Er is geen vrije ruimte in je inventory...");
					return;
				}
								setLatestDailyDiamondDay(Main.sm.getDailyDiaDay());				p.getInventory().addItem(new ItemStack(Material.DIAMOND, this.getAmountDailyDiamonds()));				p.sendMessage("�eJe dagelijkse diamonds (" + getAmountDailyDiamonds() + ") zijn in je inventory geplaatst");			}else{				p.sendMessage("�4Je dagelijkse diamonds zijn al op voor vandaag :( , kom later terug");			}		}else{			p.sendMessage("�4Je kan met jouw rank geen dagelijkse diamonds ontvangen");
			Main.plugin.sendRawMessage(p.getName(), "[{text:\"Vind info over doneren op \",color:dark_red},{text:\"[/donateur]\",color:red,hoverEvent:{action:show_text,value:Klik},clickEvent:{action:run_command,value:\"/donateur\"}}]");		}	}		protected void setOption(byte adress, boolean on, SpelerOptions executor, CommandSender a, boolean notifyAffected){		//if(executor.getOpStatus() < 1 && executor.getRank() >= 70)executor.setRank((byte) 10);		if(a == null){			return;		}		if(executor == null){			a.sendMessage("�4Error: Missing commandexecutor");			return;		}		if((adress & 0x0F) > 1 || (adress & 0x80) == 0x80){			a.sendMessage("�4Incorrecte option");			return;		}		int index = (adress & 0x0F) + 16;		int loc = adress >>> 4;		boolean self = executor == this;		if(index == 16){			/*if(loc > 4){//Pulser Options				if(self){					if((data[20] & 0x80) == 0){						a.sendMessage("�4Je hebt voor deze option de permission \"pulser.notificaties.control\" nodig");						return;					}				}else{					if(executor.getPermissionLevel() < 2){						a.sendMessage("�4Je hebt geen machtiging om deze actie uit te voeren");						return;					}				}*/
			if(loc == 5){//Vulnerable voor Pulser
				if(executor.getPermissionLevel() < 2){
					a.sendMessage("�4Je hebt geen machtiging om deze actie uit te voeren");
					return;
				}			}else if(loc == 4){//Personal Prefix				if(!self){					if(executor.getPermissionLevel() < 2){						a.sendMessage("�4Je hebt geen machtiging om deze actie uit te voeren");						return;					}				}				if(on && (data[16] & 0x08) == 0x08){					data[16] &= 0xF7;					if(Rank.getRankPrefix(this.getRank()).length() > 0){						a.sendMessage("�eDe Rank-Prefix is uitgezet (1 prefix limitatie), zet het weer aan met �c/options <spelernaam> prefix.rank on");					}				}			}else if(loc == 3){//Rank Prefix				if(!self){					if(executor.getPermissionLevel() < 2){						a.sendMessage("�4Je hebt geen machtiging om deze actie uit te voeren");						return;					}				}				if(on && (data[16] & 0x10) == 0x10){					data[16] &= 0xEF;					if(this.parent == null || this.parent.player == null || Person.getPersonalPrefix(this.parent.player.getUniqueId()).length() > 0){						a.sendMessage("�eDe Personal-Prefix is uitgezet (1 prefix limitatie), zet het weer aan met �c/options <spelernaam> prefix.personal on");					}				}			}else{
				a.sendMessage("�4Deze option bestaat niet");
				return;
			}		}else{			a.sendMessage("�4Deze option bestaat niet");			return;		}		if(!self && this.getOpStatus() > 1 && executor.getOpStatus() < 3){
			a.sendMessage("�4Niet spelen met de options van andere ops!");
			return;
		}				//if(loc == 5 && on && (data[16] & 0xC0) == 0){		//	a.sendMessage("Het heeft geen zin om de pulser te laten ticken terwijl niemand notificaties krijgt. �4Commando geannuleerd");		//	return;		//}		if(on){			data[index] |= (0x01 << loc);		}else{			data[index] &= ~(0x01 << loc);		}		//if((data[16] & 0xC0) == 0)data[16] &= 0xDF;//clear vulnerable bit als die geen abonneer en doneer notificaties krijgt		this.latestChange = System.currentTimeMillis();		if(self){			a.sendMessage("�eJe hebt je optie \"" + getAdressName(adress, false) + "\" veranderd naar " + (((data[index] & (0x01 << loc)) == 0x00)?"uit":"aan"));		}else{			if(this.parent != null){				a.sendMessage("�eJe hebt de optie \"" + getAdressName(adress, false) + "\" van " + (this.parent.name==null?"iemand":this.parent.name) + " veranderd naar " + (((data[index] & (0x01 << loc)) == 0x00)?"uit":"aan"));
				if(notifyAffected)this.parent.sendMessage("�eJe optie " + getAdressName(adress, false) + " is veranderd naar " + (((data[index] & (0x01 << loc)) == 0x00)?"uit":"aan"));			}else{				a.sendMessage("�eJe hebt de optie \"" + getAdressName(adress, false) + "\" veranderd naar " + (((data[index] & (0x01 << loc)) == 0x00)?"uit":"aan"));			}
		}	}		public void setPermission(byte adress, boolean on, boolean isStatic, SpelerOptions executor, CommandSender a, boolean notifyAffected){		if(a == null)return;		if(executor == null){			try{				a.sendMessage("�4Error: Invalid executor");			}catch(Exception e){}			return;		}		if(!isStatic)on = false;		if(adress == 0x7F){			a.sendMessage("�4Onbekende permission");
			return;		}		if((adress & 0x80) == 0x80 || (adress & 0xFE) == 0){			a.sendMessage("�4ERROR: Invalid permission");			return;		}		byte index = (byte) (adress & 0x0F);		byte loc = (byte) (adress >>> 4);				//Soft is wanneer iemand geen permission kan geven waardoor iemand benadeeld kan worden, maar ook geen benadelingen kan opheffen omdat de persoon die anders niet meer opnieuw kan opleggen		//Dus personen die permissions enkel met soft kunnen veranderen, kunnen geen Static permission die uit staat opheffen of opleggen over die permissions		boolean soft = (on == isStatic && (this.getSwitch(adress, true))?(this.getSwitch(adress, false)):true);		/*if(executor.getOpStatus() > 2){			setStaticValue(adress, on, true);			a.sendMessage("Permission veranderd");			return;		}else{			if(adress == 0x52 || adress == 0x72 || ){				if(executor.getSwitches(18, 0x40)){					set				}			}		}*/		//PermissionLevel (kan van iedereen veranderen incl. zichzelf) ({D, S0, S2} --> zie legende bovenaan pagina):		//00: Niks		//01: ~Vrij~		//02: Options		//03: ~Vrij~		//04: [0x74:D & S1]		//05: [0x74:*]		//06: ~Vrij~		//07: [0x16:*],[0x26:*],[0x36:*],[0x46:D & S1],[0x56:D & S1],[0x66:*]		//08: ~Vrij~		//09: [0x64:D & S1],[0x54:D & S1],[0x72:D & S1]		//10: ~Vrij~		//11: [0x74:*],[0x64:*],[0x54:*],[0x46:*],[0x56:*],[0x72:*]		//12: ~Vrij~		//13: [0x76:*]		//14 (CONSOLE, Owner & DEV only): ~Vrij~		//15 (DEV only): ~Vrij~		if(this.getOpStatus() < 3){
			/*			if(index == 2){//Algemene Permissions				if(loc == 7){					if(soft){						if(this.getPermissionLevel() < 9){a.sendMessage("�4Je hebt minimum Permission Level 9 nodig om dit te doen");return;}					}else{						if(this.getPermissionLevel() < 11){a.sendMessage("�4Je hebt minimum Permission Level 11 nodig om dit te doen");return;}					}				}else{					a.sendMessage("�4Deze permission bestaat nog niet");					return;				}			}else if(index == 4){				if(loc == 7){					if(soft){						if(this.getPermissionLevel() < 4){a.sendMessage("�4Je hebt minimum Permission Level 4 nodig om dit te doen");return;}					}else{						if(this.getPermissionLevel() < 5){a.sendMessage("�4Je hebt minimum Permission Level 5 nodig om dit te doen");return;}					}				}else if(loc > 4){					if(soft){						if(this.getPermissionLevel() < 9){a.sendMessage("�4Je hebt minimum Permission Level 9 nodig om dit te doen");return;}					}else{						if(this.getPermissionLevel() < 11){a.sendMessage("�4Je hebt minimum Permission Level 11 nodig om dit te doen");return;}					}				}else{
					a.sendMessage("�4Deze permission bestaat nog niet");
					return;
				}			}else if(index == 6){				if(loc == 7){
					if(this.getPermissionLevel() < 13){a.sendMessage("�4Je hebt minimum Permission Level 13 nodig om dit te doen");return;}
				}else if(loc == 6){
					if(this.getPermissionLevel() < 7){a.sendMessage("�4Je hebt minimum Permission Level 7 nodig om dit te doen");return;}
				}else if(loc == 5){
					if(soft){
						if(this.getPermissionLevel() < 7){a.sendMessage("�4Je hebt minimum Permission Level 7 nodig om dit te doen");return;}
					}else{
						if(this.getPermissionLevel() < 11){a.sendMessage("�4Je hebt minimum Permission Level 11 nodig om dit te doen");return;}
					}
				}else if(loc == 4){
					if(soft){
						if(this.getPermissionLevel() < 7){a.sendMessage("�4Je hebt minimum Permission Level 7 nodig om dit te doen");return;}
					}else{
						if(this.getPermissionLevel() < 11){a.sendMessage("�4Je hebt minimum Permission Level 11 nodig om dit te doen");return;}
					}
				}else if(loc > 0){
					if(this.getPermissionLevel() < 7){a.sendMessage("�4Je hebt minimum Permission Level 7 nodig om dit te doen");return;}
				}else{//0x06 bestaat nog niet
					a.sendMessage("�4Deze permission bestaat nog niet");
					return;
				}			}else{				a.sendMessage("�4Invalid Permission!");//Er kan een adress met oneven index worden doorgegeven
				return;			}*/
			byte permissionLevelRequired = SpelerOptions.getPermissionLevelRequired(index, loc, soft?1:2);
			if(permissionLevelRequired == 127){
				a.sendMessage("�4Onbekende permission");
				return;
			}else{
				if(executor.getPermissionLevel() < permissionLevelRequired){
					a.sendMessage("�4Voor deze operation heb je PermissionLevel " + permissionLevelRequired + " nodig, jij hebt maar PermissionLevel " + executor.getPermissionLevel());
					return;
				}
			}		}				index += 16;		byte posmask = (byte) (0x01 << loc);		byte negmask = (byte) ~posmask;		if(isStatic){			data[index + 1] |= posmask;		}else{			data[index + 1] &= negmask;		}		if(on){			data[index] |= posmask;		}else{			data[index] &= negmask;		}
		this.latestChange = System.currentTimeMillis();		this.refreshPermProperties();
		if(this.parent != null)this.parent.refreshBukkitPermissions();
		if(executor == this){
			a.sendMessage("�eJe permission \"" + SpelerOptions.getAdressName(adress, false) + "\" is nu " + (((data[index + 1] & posmask) == posmask)?"Static ":"Dynamic ") + (((data[index] & posmask) == posmask)?"�aAan":"�4Uit"));
			this.latestChange = System.currentTimeMillis();
		}else{			if(this.parent != null){				a.sendMessage("�eDe permission \"" + SpelerOptions.getAdressName(adress, false) + "\" van \"" + (this.parent.name == null?"iemand":this.parent.name) + "\" is nu " + (((data[index + 1] & posmask) == posmask)?"Static ":"Dynamic ") + (((data[index] & posmask) == posmask)?"�aAan":"�4Uit"));
				if(notifyAffected){
					if((data[index] & posmask) == posmask){
						this.parent.sendMessage("�eJe hebt nu de permission \"�2" + SpelerOptions.getAdressName(adress, false) + "�e\" (" + (((data[index + 1] & posmask) == posmask)?"Static":"Dynamic") + ')');
					}else{
						this.parent.sendMessage("�eJe hebt de permission \"�4" + SpelerOptions.getAdressName(adress, false) + "�4\" niet meer (" + (((data[index + 1] & posmask) == posmask)?"Static":"Dynamic") + ')');
					}
				}			}else{
				a.sendMessage("�eDe permission \"" + SpelerOptions.getAdressName(adress, false) + "\" is nu " + (((data[index + 1] & posmask) == posmask)?"Static ":"Dynamic ") + (((data[index] & posmask) == posmask)?"�aAan":"�4Uit"));
			}			
		}	}/*---Functions Part 2: System Ordered Functions---*/
	public void setRank(byte i){
		if(i == -128){
			return;
		}
		if(data[0] < 70){
			if(i >= 70){
				this.setPermissionLevel((byte) 13);
			}
		}else{
			if(i < 70){
				this.setPermissionLevel((byte)1);
			}
		}
			
		if(i < getDonatorRank()){
			i = getDonatorRank();
		}
		data[0] = i;
		this.latestChange = System.currentTimeMillis();
				this.refreshRank();		return;
		/*pl.removeAttachment();
		Rank.setPermissions(r, pl, p);*/
	}	protected void setStaticValue(byte adress, boolean value, boolean allowSwitchToStatic){		if(!getType(adress)){			if(allowSwitchToStatic){				setType(adress, true);			}else{				return;			}		}		setSwitch(adress, value, false);	}	public void setType(byte adress, boolean staticswitch){		setSwitch(adress, staticswitch, true);	}	public void setSwitch(byte adress, boolean on, boolean staticpart){		setSwitchWithoutUpdate(adress, on, staticpart);		refreshPermProperties();	}	protected void setLatestDailyDiamondDay(short day){
		if(this.parent == null || this.parent.pm == null)return;
		DataFieldShort df = this.parent.pm.dailyDiaDays;
		if(df == null)return;
		
		df.setValue(this.parent.kartoffelID, day);
				/*data[8] = (byte) ((day >>> 8) & 0xFF);		data[9] = (byte) (day & 0xFF);
		this.latestChange = System.currentTimeMillis();*/	}
/*---Functions Part 3: Info-functions---*/	public byte getRank(){return data[0];}	public byte getDonatorRank(){		byte a = (byte) ((data[4] >>> 4) & 0xFF);		switch (a){			case 0: return -127;			case 1: return 20;			case 2: return 25;			case 3: return 30;			case 4: return 35;		}		return -127;	}	public boolean getType(byte adress){		return getSwitch(adress, true);	}	public boolean getSwitch(byte adress, boolean staticpart){		if(adress == 0x7F)return false;		byte index = (byte) (adress & 0x0F);		if(staticpart)++index;		byte loc = (byte) (adress >>> 4);		return this.getSwitches((byte)(16 + index), (byte)(0x01 << loc));	}	public boolean DailyDiamondReady(){
		if(Main.sm == null)return false;		return Main.sm.getDailyDiaDay() > getLatestDailyDiamondDay();	}	public short getLatestDailyDiamondDay(){
		if(this.parent == null || this.parent.pm == null)return 0x7FFF;
		DataFieldShort df = this.parent.pm.dailyDiaDays;
		if(df == null)return 0x7FFF;
		try{
			return df.getValue(this.parent.kartoffelID);
		}catch(Exception e){
			return 0x7FFF;
		}		//return (short) (((short)data[8] & 0xFF) << 8 | ((short)data[9] & 0xFF));	}/*---Functions Part 4: "Behind the sc�ne"-functions---*/	public void refreshRankRequirments(){		if(data[0] > 70 && this.getOpStatus() < 1){//data[0] moet gebruikt worden om een loop uit getRank() te voorkomen, twijfelgevallen (OpStatus 1) worden niet gecorrigeerd zodat bv. offline ops niet opeens een lage rank kunnen krijgen			setRank((byte) 10);		}	}	public int getAmountDailyDiamonds(){		if(this.getDonatorRank() >= 35)return 3;		if(this.getDonatorRank() >= 30)return 2;		return 0;	}
	public static SpelerOptions getDefaultOptions(){
		byte[] array = defaultOptions.clone();
		return new SpelerOptions(array);
	}		
	/*public short getToday(){
		//long l = Calendar.getInstance().getTimeInMillis();
		//return (short) ((l - sinceDate) / 86400000);
		if(Main.sm != null){
			return Main.sm.getDailyDiaDay();
		}else{
			long l = Calendar.getInstance().getTimeInMillis();
			return (short) ((l - sinceDate) / 86400000);
		}
	}*/	
			
	protected void setSwitchWithoutUpdate(byte adress, boolean on, boolean staticpart){
		if(adress == 0x7F)return;
		byte index = (byte) (adress & 0x0F);
		if(staticpart && (index + 1 < 32))++index;
		byte loc = (byte) (adress >>> 4);
		setSwitches(index + 16, (byte)(0x01 << loc), on);
	}
	public boolean getSwitches(int index, byte switches){
		if(switches == 0x00)return false;
		return (index >= 0 && index < 32)?((data[index] & switches) == switches):false;
	}
	protected void setSwitches(int index, byte switches, boolean on){
		if(index < 0 || index >= data.length)return;
		if(on){
			data[index] |= switches;
		}else{
			data[index] &= ~switches;
		}
		this.latestChange = System.currentTimeMillis();
	}
	
	public boolean canChangeNotifReceivement(int pulserNotifID){//Ops zouden altijd mogen, maar dat is niet inbegrepen in deze functie
		if(pulserNotifID < 0 || pulserNotifID > 15 || Main.sm == null)return false;
		if((this.data[24] & 0x20) == 0x20)return true;
		
		short pulserNotif = (short) (0x8000 >>> pulserNotifID);
		if(((this.data[24] & 0x80) == 0x80) && (Main.sm.firstNotifChangeMask & pulserNotif) == pulserNotif){
			return true;
		}
		
		if(((this.data[24] & 0x40) == 0x40) && (Main.sm.secondNotifChangeMask & pulserNotif) == pulserNotif){
			return true;
		}
		
		return false;
	}
		public void setPermission(byte adress, boolean value, boolean isStatic, boolean update){		this.setSwitchWithoutUpdate(adress, value, false);		this.setSwitchWithoutUpdate(adress, isStatic, true);		if(update)this.refreshPermProperties();	}
	public void refreshRank(){
		this.refreshRankRequirments();
		parent.refreshBukkitPermissions();
		this.refreshPermProperties();		this.refreshPrefix();
	}	public void refreshPermProperties(){
		applyRankPermissions(this.getRank());				//data[10] = (data[10] & data[11]) | (temp & ~data[11]);		//PermissionReaction		byte level = this.getPermissionLevel();		if(level >= 4){			data[18] |= (0x80 & ~data[20]);			data[20] |= (0xC0 & ~data[21]);			data[22] |= (0xFE & ~data[23]);		}		if((data[22] & 0x80) == 0x80){			data[22] |= (0x7E & ~data[23]);		}		if(((data[22] & 0x20) == 0x20) && (data[22] & 0x40) == 0x00){			data[23] &= 0xDF;			data[22] &= 0xDF;		}		if(((data[22] & 0x10) == 0x10) && (data[22] & 0x20) == 0x00){			data[23] &= 0xEF;			data[22] &= 0xEF;		}	}	private void applyRankPermissions(byte r){		data[18] = (byte) (data[18] & data[19]); 		data[20] = (byte) (data[20] & data[21]);		data[22] = (byte) (data[22] & data[23]);
		data[24] = (byte) (data[24] & data[25]);
				if(r <   1)return;			data[22] |= (0x4C & ~data[23]);		if(r <  10)return;
			data[22] |= (0x30 & ~data[23]);		if(r <  20)return;
			data[24] |= (0xE0 & ~data[25]);		if(r <  35)return;
			data[20] |= (0x20 & ~data[21]);		if(r <  70)return;
			data[18] |= (0x20 & ~data[19]);			data[22] |= (0xFE & ~data[23]);
			data[24] |= (0x20 & ~data[25]);	}	public byte getOpStatus(){//0 = niet, 1 = misschien, 2 = wel, 3 = elevated		if(this == CONSOLE || this == DEV)return 3;		if(this.parent == null || this.parent.player == null){			return 1;		}else{			return (byte) ((this.parent.player.isOp())?2:0);		}	}	public byte getRealRank(){		byte r = this.getRank();		return(getOpStatus() > 1 && r < 70)?70:r;	}	public byte[] getData(){		return data.clone();	}	//public void doOpCheck(){	//	if(this.getOpStatus() < 1 && this.getRank() >= 70)this.setRank((byte) 10);	//}		public static List<String> getOptionCompletions(String in){
		in = in.toLowerCase();		ArrayList<String> ans = new ArrayList<String>();		for(int i = 0; i < optionNames.length; ++i){			if(optionNames[i].startsWith(in)){				ans.add(optionNames[i]);			}		}
		return ans;	}	public static List<String> getPermissionCompletions(String in){		in = in.toLowerCase();
		ArrayList<String> ans = new ArrayList<String>();		for(int i = 0; i < permissionNames.length; ++i){			if(permissionNames[i].startsWith(in)){				ans.add(permissionNames[i]);			}		}
		return ans;	}	//PermissionLevel (kan van iedereen veranderen incl. zichzelf) ({D, S0, S1} --> zie legende bovenaan pagina):	//00: Niks	//01: ~Vrij~	//02: Options	//03: ~Vrij~	//04: [0x78:D & S1],[0x68:D & S1],[0x58:D & S1]	//05: [0x78:*],[0x68:*],[0x58:*]	//06: ~Vrij~	//07: [0x16:*],[0x26:*],[0x36:*],[0x46:D & S1],[0x56:D & S1],[0x66:*]	//08: ~Vrij~	//09: [0x64:D & S1],[0x54:D & S1],[0x72:D & S1]	//10: ~Vrij~	//11: [0x74:*],[0x64:*],[0x54:*],[0x46:*],[0x56:*],[0x72:*]	//12: ~Vrij~	//13: [0x76:*],[0x48:*]	//14 (CONSOLE, Owner & DEV only): ~Vrij~	//15 (DEV only): ~Vrij~	protected byte getPermissionLevel(){		return (byte) ((data[7] >>> 4) & 0x0F);	}	protected void setPermissionLevel(byte b){		data[7] = (byte) ((data[7] & 0x0F) | (b & 0x0F) << 4);
		this.latestChange = System.currentTimeMillis();	}
	
	protected static byte getPermissionLevelRequired(byte adress, int operation){
		return SpelerOptions.getPermissionLevelRequired((byte)(adress & 0x0F), (byte)(adress >>> 4), operation);
	}
	
	/* //Van vorige functie die getPermissionAccessLevel heette:
	 * //-1: onbestaande
	 * // 0: geen permission
	 * // 1: get
	 * // 2: get & soft set
	 * // 3: get & * set
	 */
	
	//Operation:
	//0: get
	//1: soft set
	//2:  *   set
	
	//Veranderd naar een functie om te kijken wel PermissionLevel nodig is
	protected static byte getPermissionLevelRequired(byte index, byte loc, int operation){
		//Als de permission niet gevonden is of de operation niet klopt, wordt uiteindelijk 127 returnt omdat het hoogst mogelijke value 15 is (vanwege 4 bits) en zo niemand permission heeft om deze incorrecte dingen te doen
		if(index == 2){//Algemene Permissions
			if(loc == 7){
				if(operation == 2)return 11;//full set
				if(operation == 1)return  9;//soft set
				if(operation == 0)return  9;//     get
			}
		}else if(index == 4){
			if(loc > 4){
				if(operation == 2)return 11;//full set
				if(operation == 1)return  9;//soft set
				if(operation == 0)return  9;//     get
			}
		}else if(index == 6){
			if(loc == 7){
				if(operation == 2)return 13;//full set
				if(operation == 1)return 13;//soft set
				if(operation == 0)return 13;//     get
			}else if(loc == 6){
				if(operation == 2)return  7;//full set
				if(operation == 1)return  7;//soft set
				if(operation == 0)return  7;//     get
			}else if(loc == 5){
				if(operation == 2)return 11;//full set
				if(operation == 1)return  7;//soft set
				if(operation == 0)return  7;//     get
			}else if(loc == 4){
				if(operation == 2)return 11;//full set
				if(operation == 1)return  7;//soft set
				if(operation == 0)return  7;//     get
			}else if(loc > 0){
				if(operation == 2)return  7;//full set
				if(operation == 1)return  7;//soft set
				if(operation == 0)return  7;//     get
			}
		}else if(index == 8){
			if(loc == 7 || loc == 6 || loc == 5){
				if(operation == 2)return  5;//full set
				if(operation == 1)return  4;//soft set
				if(operation == 0)return  4;//     get
			}else if(loc == 4){
				if(operation == 2)return 13;//full set
				if(operation == 1)return 13;//soft set
				if(operation == 0)return 13;//     get
			}
		}
		return 127;
	}		protected void refreshPrefix(){		if(this.parent == null || this.parent.player == null)return;		String newName = this.parent.player.getDisplayName();		{			int i = newName.lastIndexOf("]");			if(i != -1){				newName = newName.substring(i + 1);//1 omdat het begint vanaf het character daarna //5 vanwege: �f�r en omdat het moet beginnen vanaf het character daarna			}		}		if(!newName.startsWith("�r�f")){			newName = "�r�f" + newName;		}		if((data[16] & 0x08) == 0x08){			newName = Rank.getRankPrefix(this.getRank()) + newName;		}		if((data[16] & 0x10) == 0x10){			newName = "�b" + Person.getPersonalPrefix(this.parent.player.getUniqueId()) + newName;		}		this.parent.player.setDisplayName(newName);				//String newListName = this.parent.player.getPlayerListName();		//if(newListName.charAt(0) == '�')newListName = newListName.substring(2);				//char kleur = Rank.getRankColor(this.getRank());		//if(kleur != 'f')newListName = '�' + kleur + newListName;		//this.parent.player.setPlayerListName(newListName);	}
	
	public boolean isChanged(){
		return this.latestChange > this.latestSaved;
	}
	
	public long getTimeSinceLatestSave(){
		return System.currentTimeMillis() - this.latestSaved;
	}
}
