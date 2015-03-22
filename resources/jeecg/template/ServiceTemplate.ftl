package ${bussPackage}.service.${entityPackage};

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${bussPackage}.entity.${entityPackage}.${className};
import ${bussPackage}.service.BaseService;
import ${bussPackage}.dao.${entityPackage}.${className}Dao;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Controller<br>
 * <b>作者：</b>xxxxx<br>
 * <b>日期：</b> Nov 19, 2014 <br>
 * <b>版权所有： 2014，tech.7.com 第七大道<br>
 */ 
@Service("$!{lowerName}Service")
public class ${className}Service extends BaseService<${className}> {
	private final static Logger log= Logger.getLogger(${className}Service.class);
	

	

	@Autowired
    private ${className}Dao dao;

		
	public ${className}Dao getDao() {
		return dao;
	}

}
