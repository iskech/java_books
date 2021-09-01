package com.iskech.mybatis.customer.v1.builder;

import com.iskech.mybatis.customer.v1.base.IConfiguration;
import com.iskech.mybatis.customer.v1.base.IEnviremation;
import com.iskech.mybatis.customer.v1.mapping.IBounding;
import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import com.iskech.mybatis.customer.v1.parsing.IXNode;
import com.iskech.mybatis.customer.v1.parsing.IXPathParser;
import com.iskech.mybatis.customer.v1.pool.IDataSource;
import com.iskech.mybatis.customer.v1.pool.IPooledDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IXMLConfigBuilder {
    private String environment;

    private final IXPathParser ixPathParser;

    public IXMLConfigBuilder(IXPathParser ixPathParser) {
        this.ixPathParser = ixPathParser;
    }

    public IConfiguration parse() {
        IConfiguration iConfiguration = new IConfiguration();
        //解析根节点
        IXNode root = ixPathParser.evalNode("/configuration");
        //解析环境节点
        IXNode environments = root.evalNode("environments");
        IEnviremation iEnviremation = environmentsElement(environments);
        iConfiguration.setIEnviremation(iEnviremation);
        //解析mappers节点
        mappersElement(root.evalNode("mappers"), iConfiguration);
        return iConfiguration;

    }

    private void mappersElement(IXNode mappers, IConfiguration iConfiguration) {
        List<IXNode> mapperList = mappers.evalNodes("mapper");
        for (IXNode ixNode : mapperList) {
            String resource = ixNode.getStringAttribute("resource");
            InputStream mapperInputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
            IXPathParser mapperPathParser = new IXPathParser(mapperInputStream);
            IXMLMapperBuilder ixmlMapperBuilder = new IXMLMapperBuilder(mapperPathParser, iConfiguration, "mappers");
            ixmlMapperBuilder.parse();
        }

    }


    private IEnviremation environmentsElement(IXNode context) {
        List<IXNode> environmentList = context.evalNodes("environment");
        IXNode dataSource = environmentList.get(1).evalNode("dataSource");
        List<IXNode> propertyList = dataSource.evalNodes("property");
        String driver = null;
        String url = null;
        String username = null;
        String password = null;
        for (IXNode property : propertyList) {
            switch (property.getStringAttribute("name")) {
                case "driver":
                    driver = property.getStringAttribute("value");
                    break;
                case "url":
                    url = property.getStringAttribute("value");
                    break;
                case "username":
                    username = property.getStringAttribute("value");
                    break;
                case "password":
                    password = property.getStringAttribute("value");
                    break;
                default:
                    break;
            }
        }
        DataSource iDataSource = null;
        try {
            iDataSource = new IPooledDataSource(driver, url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        IEnviremation iEnviremation = new IEnviremation();
        iEnviremation.setDateSource(iDataSource);


        return iEnviremation;
    }

    public static void main(String[] args) {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        IXPathParser ixPathParser = new IXPathParser(resourceAsStream);
        new IXMLConfigBuilder(ixPathParser).parse();
    }
}
