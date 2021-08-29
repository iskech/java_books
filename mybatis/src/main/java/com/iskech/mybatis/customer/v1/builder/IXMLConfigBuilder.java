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
        //   List<IXNode> ixNodes = ixPathParser.evalNodes("");
        //解析环境节点
        IXNode environments = root.evalNode("environments");
        IEnviremation iEnviremation = environmentsElement(environments);
        Map<String, IMappedStatement> iMappedStatementMap = mappersElement(root.evalNode("mappers"));

        iConfiguration.setIEnviremation(iEnviremation);
        iConfiguration.setMappedStatementMap(iMappedStatementMap);
        return iConfiguration;

    }

    private Map<String, IMappedStatement> mappersElement(IXNode mappers) {
        List<IXNode> mapperList = mappers.evalNodes("mapper");
        HashMap<String, IMappedStatement> iMappedStatementHashMap = new HashMap<>();
        for (IXNode ixNode : mapperList) {
            String resource = ixNode.getStringAttribute("resource");
            parseMapper(resource,iMappedStatementHashMap);
        }
        return iMappedStatementHashMap;
    }

    private void parseMapper(String resource ,Map iMappedStatementHashMap) {
        String namespace = "com.iskech.mybatis.customer.v1.provider.mapper.CityMapper";
        String selectId = "listByName";
        String paramType = "string";
        String resultType = "com.iskech.mybatis.customer.v1.api.model.City";
        String selectSql = "select * from city where name like ?";
        IBounding iBounding = new IBounding();
        iBounding.setSql(selectSql);
        IMappedStatement iMappedStatement = new IMappedStatement();
        iMappedStatement.setIbounding(iBounding);
        iMappedStatement.setParameterType(paramType);
        iMappedStatement.setResultType(resultType);
        String key = namespace + "." + selectId;
        iMappedStatement.setId(key);
        iMappedStatementHashMap.put(key,iMappedStatement);

    }

    private void parseMapper2(String resource ,Map iMappedStatementHashMap) {
        IXNode root = ixPathParser.evalNode("/mapper");
        //<mapper namespace="com.iskech.mybatis.provider.mapper.OrderLogMapper">
        String namespace = root.getStringAttribute("namespace");
        // <select id="listByCargoOrderCode" resultMap="BaseResultMap">
        //解析 select update delete
        List<IXNode> selectList = root.evalNodes("select");
        IXNode select1 = selectList.get(0);
        String selectId = select1.getStringAttribute("id");
        String paramType = select1.getStringAttribute("parameterType");
        String resultType = select1.getStringAttribute("resultType");
        String selectSql = select1.getStringBody();

        IBounding iBounding = new IBounding();
        iBounding.setSql(selectSql);
        IMappedStatement iMappedStatement = new IMappedStatement();
        iMappedStatement.setIbounding(iBounding);
        iMappedStatement.setParameterType(paramType);
        iMappedStatement.setResultType(resultType);

        String key = namespace + "." + selectId;
        iMappedStatementHashMap.put(key,iMappedStatement);

    }

    private IEnviremation environmentsElement(IXNode context) {
        List<IXNode> environmentList = context.evalNodes("environment");
        IXNode dataSource = environmentList.get(0).evalNode("dataSource");
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
