package code;

import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;


public class CodeUtil {

	public static void main(String[] args) {
		/** 此处修改成你的 表名 和 中文注释***/
		 String tableName="t_ccu_port_connection"; //
		 String codeName ="CCU端口连接性检测信息表";//中文注释  当然你用英文也是可以的 
		 String entityPackage ="center";//实体包
		 String keyType = FtlDef.KEY_TYPE_02;//主键生成方式 01:UUID  02:自增
		 CodeGenerateFactory.codeGenerate(tableName, codeName,entityPackage,keyType,false);
	}
}
