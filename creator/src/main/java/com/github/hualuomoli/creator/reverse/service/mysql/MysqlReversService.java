package com.github.hualuomoli.creator.reverse.service.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hualuomoli.creator.reverse.base.EntityBaseCreater;
import com.github.hualuomoli.creator.reverse.base.MapperBaseCreater;
import com.github.hualuomoli.creator.reverse.base.ServiceBaseCreater;
import com.github.hualuomoli.creator.reverse.base.XmlBaseCreater;
import com.github.hualuomoli.creator.reverse.base.mysql.MysqlEntityBaseCreater;
import com.github.hualuomoli.creator.reverse.base.mysql.MysqlMapperBaseCreater;
import com.github.hualuomoli.creator.reverse.base.mysql.MysqlServiceBaseCreater;
import com.github.hualuomoli.creator.reverse.base.mysql.MysqlXmlBaseCreater;
import com.github.hualuomoli.creator.reverse.component.mysql.parser.MysqlParser;
import com.github.hualuomoli.creator.reverse.component.mysql.service.MysqlDBService;
import com.github.hualuomoli.creator.reverse.component.parser.Parser;
import com.github.hualuomoli.creator.reverse.component.service.DBService;
import com.github.hualuomoli.creator.reverse.query.EntityQueryCreater;
import com.github.hualuomoli.creator.reverse.query.MapperQueryCreater;
import com.github.hualuomoli.creator.reverse.query.ServiceQueryCreater;
import com.github.hualuomoli.creator.reverse.query.XmlQueryCreater;
import com.github.hualuomoli.creator.reverse.query.mysql.MysqlEntityQueryCreater;
import com.github.hualuomoli.creator.reverse.query.mysql.MysqlMapperQueryCreater;
import com.github.hualuomoli.creator.reverse.query.mysql.MysqlServiceQueryCreater;
import com.github.hualuomoli.creator.reverse.query.mysql.MysqlXmlQueryCreater;
import com.github.hualuomoli.creator.reverse.service.AbstractReversService;

@Service(value = "com.github.hualuomoli.creator.reverse.service.mysql.MysqlReversService")
public class MysqlReversService extends AbstractReversService {

  @Autowired
  private MysqlDBService dbService;
  @Autowired
  private MysqlParser parser;

  // base
  @Autowired
  private MysqlEntityBaseCreater entityBaseCreater;
  @Autowired
  private MysqlXmlBaseCreater xmlBaseCreater;
  @Autowired
  private MysqlMapperBaseCreater mapperBaseCreater;
  @Autowired
  private MysqlServiceBaseCreater serviceBaseCreater;
  // query
  @Autowired
  private MysqlEntityQueryCreater entityQueryCreater;
  @Autowired
  private MysqlXmlQueryCreater xmlQueryCreater;
  @Autowired
  private MysqlMapperQueryCreater mapperQueryCreater;
  @Autowired
  private MysqlServiceQueryCreater serviceQueryCreater;

  @Override
  protected DBService dbService() {
    return dbService;
  }

  @Override
  protected Parser parser() {
    return parser;
  }

  @Override
  protected EntityBaseCreater entityBaseCreater() {
    return entityBaseCreater;
  }

  @Override
  protected XmlBaseCreater xmlBaseCreater() {
    return xmlBaseCreater;
  }

  @Override
  protected MapperBaseCreater mapperBaseCreater() {
    return mapperBaseCreater;
  }

  @Override
  protected ServiceBaseCreater serviceBaseCreater() {
    return serviceBaseCreater;
  }

  @Override
  protected EntityQueryCreater entityQueryCreater() {
    return entityQueryCreater;
  }

  @Override
  protected XmlQueryCreater xmlQueryCreater() {
    return xmlQueryCreater;
  }

  @Override
  protected MapperQueryCreater mapperQueryCreater() {
    return mapperQueryCreater;
  }

  @Override
  protected ServiceQueryCreater serviceQueryCreater() {
    return serviceQueryCreater;
  }

}
