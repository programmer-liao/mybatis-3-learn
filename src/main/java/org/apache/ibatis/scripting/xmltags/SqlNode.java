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
package org.apache.ibatis.scripting.xmltags;

/**
 * 用来解析动态SQL
 * @author Clinton Begin
 */
public interface SqlNode {
  /**
   * 1.该方法会根据用户传入的实参解析改SqlNode所记录的SQL动态节点，
   * 并调用DynamicContext.appendSql()方法将解析后的SQL片段追加到DynamicContext.sqlBuilder中保存
   * 2.当SQL节点下的所有SqlNode完成解析后，我们就可以从DynamicContext中获取一条动态生成的完整的SQL语句
   */
  boolean apply(DynamicContext context);
}
