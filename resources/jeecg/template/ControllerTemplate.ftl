package ${bussPackage}.controller.${entityPackage};

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ${bussPackage}.controller.BaseAction;
import com.road7.vrm.util.HtmlUtil;
import ${bussPackage}.entity.BaseEntity.DELETED;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.page.${entityPackage}.${className}Page;
import ${bussPackage}.service.${entityPackage}.${className}Service;
 
/**
 * 
 * <br>
 * <b>功能：</b>${className}Controller<br>
 * <b>作者：</b>${user}<br>
 * <b>日期：</b> ${time} <br>
 * <b>版权所有： 2014，${organization}<br>
 */ 
@Controller
@RequestMapping("/${lowerName}") 
public class ${className}Controller extends BaseAction{
	
	private final static Logger log= Logger.getLogger(${className}Controller.class);
	
	@Autowired
	private ${className}Service ${lowerName}Service; 
	
	
	/**
	 * 
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/list") 
	public ModelAndView  list(${className}Page page,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("${bussPackage}/${entityPackage}/${lowerName}",context); 
	}
	
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList") 
	public void  datalist(${className}Page page,HttpServletResponse response) throws Exception{
		List<${className}> dataList = ${lowerName}Service.queryByList(page);
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getPager().getRowCount());
		jsonMap.put("rows", dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(${className} entity,Integer[] typeIds,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			${lowerName}Service.add(entity);
		}else{
			${lowerName}Service.update(entity);
		}
		sendSuccessMessage(response, "保存成功~");
	}
	
	
	@RequestMapping("/getId")
	public void getId(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap();
		${className} entity  = ${lowerName}Service.queryById(id);
		if(entity  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", entity);
		HtmlUtil.writerJson(response, context);
	}
	
	
	
	@RequestMapping("/delete")
	public void delete(String[] id,HttpServletResponse response) throws Exception{
		${lowerName}Service.delete(id);
		sendSuccessMessage(response, "删除成功");
	}

}
