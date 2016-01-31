package ${package}.entity;

import java.io.Serializable;
<#list imports as import>
import ${import.name};
</#list>

/**
 * 实体类
 * @version ${version}
 * @date:  ${currentTime}
 */

public class ${class} implements Serializable  {
  
        

     <#list properties as prop>
        private ${prop.type} ${prop.name};
      </#list>
      
        public ${class}(){
        	
        }
      
       <#list properties as prop>
        public ${prop.type} get${prop.name?cap_first}(){

             return ${prop.name};

        }
        public void set${prop.name?cap_first}(${prop.type} ${prop.name}){

             this.${prop.name} = ${prop.name};

         }
         
         

      </#list>
      }
