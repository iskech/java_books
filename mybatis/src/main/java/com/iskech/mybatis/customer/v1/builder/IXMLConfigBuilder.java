package com.iskech.mybatis.customer.v1.builder;

import com.iskech.mybatis.customer.v1.base.IConfiguration;
import com.iskech.mybatis.customer.v1.base.IEnviremation;
import com.iskech.mybatis.customer.v1.mapping.IBounding;
import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import com.iskech.mybatis.customer.v1.parsing.IXNode;
import com.iskech.mybatis.customer.v1.parsing.IXPathParser;
import com.iskech.mybatis.customer.v1.pool.IDataSource;

import java.io.InputStream;
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
        IDataSource iDataSource = new IDataSource();
        IEnviremation iEnviremation = new IEnviremation();
        iEnviremation.setDateSource(iDataSource);
        for (IXNode property : propertyList) {
            switch (property.getStringAttribute("name")) {
                case "driver":
                    iDataSource.setDriver(property.getStringAttribute("value"));
                    break;
                case "url":
                    iDataSource.setUrl(property.getStringAttribute("value"));
                    break;
                case "username":
                    iDataSource.setUsername(property.getStringAttribute("value"));
                    break;
                case "password":
                    iDataSource.setPassword(property.getStringAttribute("value"));
                    break;
                default:
                    break;
            }
        }


        return iEnviremation;
    }

    public static void main(String[] args) {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        IXPathParser ixPathParser = new IXPathParser(resourceAsStream);
        new IXMLConfigBuilder(ixPathParser).parse();
    }
}
