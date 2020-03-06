package resource.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ResourceDAO {
	//자료 등록
	public int insertResource(ResourceBean rBean);
	
	//자료 수정
	public void updateResource(ResourceBean rBean);
	
	//자료 삭제
	public void resourceDelete(int res_no);
	
	//자료 목록
	public List selectAllResources(Map pagingMap,String opt, String condition);
	
	//전체 글 개수
	public int selectTotResources(String opt, String condition);
	
	//자료 내용
	public ResourceBean resourceView(int res_no);
	
	//자료 검색
	public ArrayList<ResourceBean> resourceSelect(HashMap<String, Object> listOpt);
}
