package codeGenerate.factory;

import codeGenerate.CommonPageParser;
import codeGenerate.CreateBean;
import codeGenerate.def.CodeResourceUtil;

import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

public class CodeGenerateFactory
{
  private static final Log log = LogFactory.getLog(CodeGenerateFactory.class);
  private static String url = CodeResourceUtil.URL;
  private static String username = CodeResourceUtil.USERNAME;
  private static String passWord = CodeResourceUtil.PASSWORD;

  private static String buss_package = CodeResourceUtil.bussiPackage;
  private static String projectPath = getProjectPath();

  /**
   * @param tableName：表名
   * @param codeName：注释
   * @param entityPackage：实体包 
   * @param keyType：主键生成方式  01:UUID  02:自增
   * @param isGenerateJsp:是否生产jsp页面和js文件
   */
  public static void codeGenerate(String tableName, String codeName, String entityPackage, String keyType,boolean isGenerateJsp)
  {
    CreateBean createBean = new CreateBean();
    createBean.setMysqlInfo(url, username, passWord);

    String className = createBean.getTablesNameToClassName(tableName);
    String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());

    String srcPath = projectPath + CodeResourceUtil.source_root_package + "\\";

    String pckPath = srcPath + CodeResourceUtil.bussiPackageUrl + "\\";

    String webPath = projectPath + CodeResourceUtil.web_root_package + "\\view\\" + CodeResourceUtil.bussiPackageUrl + "\\";

    String modelPath = "page\\" + entityPackage + "\\" + className + "Page.java";
    String beanPath = "entity\\" + entityPackage + "\\" + className + ".java";
    String mapperPath = "dao\\" + entityPackage + "\\" + className + "Dao.java";
    String servicePath = "service\\" + entityPackage + "\\" + className + "Service.java";
    String controllerPath = "controller\\" + entityPackage + "\\" + className + "Controller.java";
    String sqlMapperPath = "mapper\\" + entityPackage + "\\" + className + "Mapper.xml";
    webPath = webPath + entityPackage + "\\";

    String jspPath = lowerName + ".jsp";
    String jsPath = "page-" + lowerName + ".js";

    VelocityContext context = new VelocityContext();
    context.put("className", className);
    context.put("lowerName", lowerName);
    context.put("codeName", codeName);
    context.put("tableName", tableName);
    context.put("bussPackage", buss_package);
    context.put("entityPackage", entityPackage);
    context.put("keyType", keyType);
    context.put("user", System.getProperty("user.name"));
    context.put("time", new Date());
    context.put("organization",CodeResourceUtil.getOrganization());
    
    try
    {
      context.put("feilds", createBean.getBeanFeilds(tableName));
    } catch (Exception e) {
      e.printStackTrace();
    }

    try
    {
      Map sqlMap = createBean.getAutoCreateSql(tableName);
      context.put("columnDatas", createBean.getColumnDatas(tableName));
      context.put("SQL", sqlMap);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath, beanPath);
    CommonPageParser.WriterPage(context, "PageTemplate.ftl", pckPath, modelPath);
    CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath, mapperPath);
    CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", pckPath, servicePath);
    CommonPageParser.WriterPage(context, "MapperTemplate.xml", pckPath, sqlMapperPath);
    CommonPageParser.WriterPage(context, "ControllerTemplate.ftl", pckPath, controllerPath);
    if(isGenerateJsp){
	    CommonPageParser.WriterPage(context, "jspTemplate.ftl", webPath, jspPath);
	    CommonPageParser.WriterPage(context, "jsTemplate.ftl", webPath, jsPath);
    }

    log.info("----------------------------代码生成完毕---------------------------");
  }

  public static String getProjectPath()
  {
    String path = System.getProperty("user.dir").replace("\\", "/") + "/";
    return path;
  }
  
}