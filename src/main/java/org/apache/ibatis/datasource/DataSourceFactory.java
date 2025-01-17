/*
 *    Copyright 2009-2022 the original author or authors.
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
package org.apache.ibatis.datasource;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * DataSourceFactory 数据源工厂（使用工厂方法模式）
 * 实现：PooledDataSource 和 unpooledDataSource
 * @author Clinton Begin
 */
public interface DataSourceFactory {

  /**
   * 设置数据源的相关属性，一般紧跟在初始化完成之后
   */
  void setProperties(Properties props);

  /**
   * 创建数据源的方法接口
   */
  DataSource getDataSource();

}
