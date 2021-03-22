package ${package.Controller};
import java.util.List;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.web.bind.annotation.RequestBody;
import com.klb.portal.domain.MyPage;
import ${package.Entity}.${entity};

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@Api(tags = "${table.controllerName}", description = "${table.comment!}")
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/${entity?uncap_first}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName} ${entity?uncap_first}Service;
    
  /**
  * 分页查询
  * 
  */
    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<IPage<${entity}>> page${entity}(@RequestBody MyPage<${entity}> page) {
        return success(${entity?uncap_first}Service.page(page, null));
    }
    
  /**
  * 非分页查询多条数据
  * 
  */
    @ApiOperation("非分页查询多条数据")
    @PostMapping("/list")
    public R<List<${entity}>> list${entity}(${entity} ${entity?uncap_first}) {
         
        return null;
    }

  /**
  * 查询一条数据
  * 
  */
    @ApiOperation("查询一条数据")
    @PostMapping("/get")
    public R<${entity}> get${entity}(${entity} ${entity?uncap_first}) {
         
        return null;
    }

  /**
   * 增加一条数据
   * 
   */
    @ApiOperation("增加或修改一条数据")
    @PostMapping("/add")
    public R<String> add${entity}(${entity} ${entity?uncap_first}) {
         
        return null;
    }
    
    /**
    * 修改数据
    * 
    */
    @ApiOperation("修改数据")
    @PostMapping("/update")
    public R<String> update${entity}(${entity} ${entity?uncap_first}) {
         
        return null;
    }
    
    /**
    * 删除数据
    * 
    */
    @ApiOperation("删除数据")
    @PostMapping("/remove")
    public R<String> remove${entity}(${entity} ${entity?uncap_first}) {
         
        return null;
    }
    
}
</#if>
