package com.github.hualuomoli.config.base;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

/**
 * JTA
 */
@Configuration(value = "com.github.hualuomoli.config.base.JtaTransactionConfig")
public class JtaTransactionConfig {

  @Autowired
  private UserTransactionManager atomikosTransactionManager;
  @Autowired
  private UserTransaction atomikosUserTransaction;

  @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
  public UserTransactionManager atomikosTransactionManager() {
    UserTransactionManager userTransactionManager = new UserTransactionManager();
    userTransactionManager.setForceShutdown(true);

    return userTransactionManager;
  }

  @Bean(name = "atomikosUserTransaction")
  public UserTransaction atomikosUserTransaction() throws SystemException {
    UserTransaction userTransaction = new UserTransactionImp();
    userTransaction.setTransactionTimeout(300);

    return userTransaction;
  }

  @Bean(name = "jtaTransactionManager")
  public JtaTransactionManager jtaTransactionManager() {
    JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
    jtaTransactionManager.setTransactionManager(atomikosTransactionManager);
    jtaTransactionManager.setUserTransaction(atomikosUserTransaction);
    jtaTransactionManager.setAllowCustomIsolationLevels(true);

    return jtaTransactionManager;
  }

}
