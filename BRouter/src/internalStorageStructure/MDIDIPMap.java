package internalStorageStructure;

import java.util.List;



import model.Idmap;

/*
 * md µÄIDºÍpanidÓ³Éä
 */
public class MDIDIPMap {
	List<Idmap> map = null;
	private static MDIDIPMap instance = null;
	AbstractMDMapObtainer obtainStrategy;
	private MDIDIPMap(AbstractMDMapObtainer strategey) {
		map=strategey.getMap();
	}
		
	public static MDIDIPMap getInstance() {
		if (instance == null) { 
		      //instance = new MDIDIPMap(new PackageMDMapObtainer()); 
		      instance = new MDIDIPMap(new DBMDMapObtainer());     
	    }
		return instance;
	}
	public void addMapItem(String id, String panid){
		Idmap id_panid=new Idmap();
		id_panid.setId(id);
		id_panid.setPanid(panid);
		if(!map.contains(id_panid))
			map.add(id_panid);
	}
	public void print(){
		for(int i=0;i<map.size();i++){
			int high=map.get(i).getPanid().charAt(0);
			int low=map.get(i).getPanid().charAt(1);
			System.out.println(map.get(i).getId()+","+high+" "+low);	
		}
	}
	public char[] QueryPanid(String mdid){
		for(int i=0;i<map.size();i++){
			if(map.get(i).getId().equals(mdid)){
				char[]panid=new char[2];
				String result = map.get(i).getPanid(); 
		        result.getChars(0, 2, panid, 0);
		        return panid;
			}
		}
		return null;
		
	}
	 public String getGID(String panid){
		 for(int i=0;i<map.size();i++){
				if(map.get(i).getPanid().equals(panid)){
					String mdid = map.get(i).getId(); 
			        return mdid;
				}
			}
	        return "0000000000000000";
	    }
	public static void main(String[] args) {
		//MDIDIPMap.getInstance().print();
		char[]panid={0x2,0x3};
		String string=new String(panid);
		String id=MDIDIPMap.getInstance().getGID(string);
		System.out.println("id:"+id);
	}
}
