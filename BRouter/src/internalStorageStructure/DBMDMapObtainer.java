package internalStorageStructure;

import java.util.List;

import JDO.IDMapOperator;

import model.Idmap;

public class DBMDMapObtainer extends AbstractMDMapObtainer{

	@Override
	public List<Idmap> getMap() {
		// TODO Auto-generated method stub
		return new IDMapOperator().QueryAllIDIPMap();
	}

}
