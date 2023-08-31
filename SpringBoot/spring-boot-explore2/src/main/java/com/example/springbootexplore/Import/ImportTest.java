package com.example.springbootexplore.Import;

import com.example.springbootexplore.Import.bean.ImportBeanA;
import com.example.springbootexplore.Import.bean.ImportBeanB;
import com.example.springbootexplore.Import.bean.ImportBeanC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @import用于把组件加入到ioc容器，引入带有@Configuration的配置文件，是需要先实例这个配置文件再进行相关操作
 */
@Import({ImportBeanA.class,SelfImportSelector.class,SelfImportBeanDefinitionRegistrar.class})
@Configuration
public class ImportTest {

    @Autowired
    private ImportBeanA importBeanA;
    @Autowired
    private ImportBeanB importBeanB;
    @Autowired
    private ImportBeanC importBeanC;

    public void importTest(){
        System.out.println("\033[32m" + "------------------------------------------" + "\033[0m");
        System.out.println(importBeanA.getClass());
        System.out.println(importBeanB.getClass());
        System.out.println(importBeanC.getClass());
    }

}
