package generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

//演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

 /**
  * <p>
  * 读取控制台内容
  * </p>
  */
 public static String scanner(String tip) {
     Scanner scanner = new Scanner(System.in);
     StringBuilder help = new StringBuilder();
     help.append("请输入" + tip + "：");
     System.out.println(help.toString());
     if (scanner.hasNext()) {
         String ipt = scanner.next();
         if (StringUtils.isNotBlank(ipt)) {
             return ipt;
         }
     }
     throw new MybatisPlusException("请输入正确的" + tip + "！");
 }

 public static void main(String[] args) {
     // 代码生成器
     AutoGenerator mpg = new AutoGenerator();

     // 全局配置
     GlobalConfig gc = new GlobalConfig();
     String projectPath = System.getProperty("user.dir");
     gc.setOutputDir(projectPath + "/src/main/java");
     Map<String, String> map = System.getenv();
     String userName = map.get("USERNAME");// 获取用户名win
     String currentUser = System.getProperty("user.name");
     gc.setAuthor(currentUser);
     gc.setOpen(false);
      //gc.setIdType(IdType.AUTO);
     gc.setSwagger2(true);
     mpg.setGlobalConfig(gc);
     
     // 数据源配置
     DataSourceConfig dsc = new DataSourceConfig();
    // dsc.setUrl("jdbc:mysql://10.0.1.183:3306/ry?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
     dsc.setUrl("jdbc:postgresql://39.105.80.128:5432/duorou?currentSchema=app_goods&useUnicode=true&useSSL=false&characterEncoding=utf8");
      dsc.setSchemaName("public");
     dsc.setDriverName("org.postgresql.Driver");
     dsc.setUsername("postgres");
     dsc.setPassword("lpf888");
     mpg.setDataSource(dsc);

     // 包配置
     PackageConfig pc = new PackageConfig();
     pc.setModuleName(scanner("模块名"));
     pc.setParent("com.klb.portal.crud"); //业务功能目录
   
     mpg.setPackageInfo(pc);

     // 自定义配置
     InjectionConfig cfg = new InjectionConfig() {
         @Override
         public void initMap() {
             Map<String, Object> map = new HashMap<>();
             map.put("packageVo", pc.getParent()+".vo");
             this.setMap(map);
         }
     };

     // 如果模板引擎是 freemarker
     String templatePath = "/templates/mapper.xml.ftl";
     // 如果模板引擎是 velocity
     // String templatePath = "/templates/mapper.xml.vm";

     // 自定义输出配置
     List<FileOutConfig> focList = new ArrayList<>();
     // 自定义配置会被优先输出
     focList.add(new FileOutConfig(templatePath) {
         @Override
         public String outputFile(TableInfo tableInfo) {
             // 自定义输出文件名
             return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                     + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
         }
     });
		/*
		 * focList.add(new FileOutConfig("/templates/validGroup.java.ftl") {
		 * 
		 * @Override public String outputFile(TableInfo tableInfo) { // 自定义输出文件名 return
		 * projectPath + "/src/main/java/" +
		 * pc.getParent().replace(".","/")+"/"+pc.getEntity()+"/"+
		 * tableInfo.getEntityName() + "ValidGroup.java" ; } });
		 */
     
		/*
		 * focList.add(new FileOutConfig("/templates/vo.java.ftl") {
		 * 
		 * @Override public String outputFile(TableInfo tableInfo) { // 自定义输出文件名 return
		 * projectPath + "/src/main/java/" + pc.getParent().replace(".","/")+"/vo/"+
		 * tableInfo.getEntityName() + "Vo.java" ; } });
		 */
     cfg.setFileOutConfigList(focList);
     mpg.setCfg(cfg);

     // 配置模板
     TemplateConfig templateConfig = new TemplateConfig();

     // 配置自定义输出模板
     // templateConfig.setEntity();
     // templateConfig.setService();
     // templateConfig.setController();
     templateConfig.setController("/templates/controller.java");
     
     templateConfig.setXml(null);
     mpg.setTemplate(templateConfig);

     // 策略配置
     StrategyConfig strategy = new StrategyConfig();
     strategy.setNaming(NamingStrategy.underline_to_camel);
     strategy.setColumnNaming(NamingStrategy.underline_to_camel);
     strategy.setEntityBuilderModel(true);
     
    // strategy.setSuperEntityClass("");
     strategy.setEntityLombokModel(false);
     strategy.setRestControllerStyle(true);
     //strategy.setSuperControllerClass("com.baomidou.mybatisplus.extension.api.ApiController");
     strategy.setSuperControllerClass("com.klb.portal.crud.upper.KlbController");
      
     strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
     //strategy.setSuperEntityColumns("id");
     strategy.setControllerMappingHyphenStyle(true);
    // strategy.setTablePrefix(pc.getModuleName() + "_","demo_","ypl_"); // 表前缀，生成代码时前缀略去
     mpg.setStrategy(strategy);
     mpg.setTemplateEngine(new FreemarkerTemplateEngine());
     mpg.execute();
     
    
 }

}