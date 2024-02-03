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
 * IfSqlNode对应的动态SQL节点是＜if＞节点
 * @author Clinton Begin
 */
public class IfSqlNode implements SqlNode {

  /**
   * 用于解析<if>节点中的text表达式的值
   */
  private final ExpressionEvaluator evaluator;

  /**
   * 记录了<if>节点中的text表达式的值
   */
  private final String test;

  /**
   * 记录了<if>节点的子节点
   */
  private final SqlNode contents;

  public IfSqlNode(SqlNode contents, String test) {
    this.test = test;
    this.contents = contents;
    this.evaluator = new ExpressionEvaluator();
  }

  @Override
  public boolean apply(DynamicContext context) {
    // 检测其test表达式是否为true，然后根据test表达式的结果，决定是否执行其子节点的apply()方法
    if (evaluator.evaluateBoolean(test, context.getBindings())) {
      contents.apply(context);
      return true;
    }
    return false;
  }

}
