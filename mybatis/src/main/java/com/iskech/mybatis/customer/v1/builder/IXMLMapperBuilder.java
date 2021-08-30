/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iskech.mybatis.customer.v1.builder;

import com.iskech.mybatis.customer.v1.base.IConfiguration;
import com.iskech.mybatis.customer.v1.mapping.IBounding;
import com.iskech.mybatis.customer.v1.mapping.IMappedStatement;
import com.iskech.mybatis.customer.v1.parsing.IXNode;
import com.iskech.mybatis.customer.v1.parsing.IXPathParser;
import java.util.*;

/**
 * @author liujx
 */
public class IXMLMapperBuilder {
    private final IConfiguration configuration;
    private final IXPathParser parser;
    private final String resource;


    public IXMLMapperBuilder(IXPathParser parser, IConfiguration configuration, String resource) {
        this.configuration = configuration;
        this.parser = parser;
        this.resource = resource;
    }

    public void parse() {
        //解析元素到全局配置 configuration
        configurationElement(parser.evalNode("/mapper"));
    }

    private void configurationElement(IXNode context) {
        try {
            String namespace = context.getStringAttribute("namespace");
            if (namespace == null || namespace.equals("")) {
                throw new RuntimeException("Mapper's namespace cannot be empty");
            }
            //解析 select ,insert ,update delete元素
            buildStatementFromContext(context.evalNodes("select|insert|update|delete"),namespace);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
        }
    }

    private void buildStatementFromContext(List<IXNode> list) {
        buildStatementFromContext(list, null);
    }

    private void buildStatementFromContext(List<IXNode> list, String requiredDatabaseId) {
        for (IXNode context : list) {
            //构建 mappedStatement
            IMappedStatement iMappedStatement = builderMappedStatement(context,requiredDatabaseId);
            //添加 mappedStatement 至configuration
            if(Objects.isNull( configuration.getMappedStatementMap())){
                HashMap<String, IMappedStatement> stringIMappedStatementHashMap = new HashMap<>();
                configuration.setMappedStatementMap(stringIMappedStatementHashMap);
            }
            configuration.getMappedStatementMap().put(iMappedStatement.getId(), iMappedStatement);
        }
    }

    private IMappedStatement builderMappedStatement(IXNode root ,String namespace) {
        // <select id="listByCargoOrderCode" resultMap="BaseResultMap">
        //解析 select update delete
        IXNode select1 = root;
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
        iMappedStatement.setId(key);
        return iMappedStatement;
    }
}
