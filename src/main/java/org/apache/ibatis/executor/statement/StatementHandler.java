/*
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

/**
 * 语句处理器
 * @author Clinton Begin
 */
public interface StatementHandler {

  /**
   * 从连接中获取一个Statement
   */
  Statement prepare(Connection connection, Integer transactionTimeout) throws SQLException;

  /**
   * 绑定statement时执行时需要的实参
   */
  void parameterize(Statement statement) throws SQLException;

  /**
   * 批量执行SQL
   */
  void batch(Statement statement) throws SQLException;

  /**
   * 执行update/insert/delete语句
   */
  int update(Statement statement) throws SQLException;

  /**
   * 执行select语句
   */
  <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;

  <E> Cursor<E> queryCursor(Statement statement) throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();

}
