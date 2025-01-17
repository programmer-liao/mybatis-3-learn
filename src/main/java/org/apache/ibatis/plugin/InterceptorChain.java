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
package org.apache.ibatis.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 责任链模式 - 拦截器执行链
 * @author Clinton Begin
 */
public class InterceptorChain {

  /**
   * 记录了mybatis-config.xml文件中配置的拦截器
   */
  private final List<Interceptor> interceptors = new ArrayList<>();

  /**
   * 遍历interceptors
   */
  public Object pluginAll(Object target) {
    // 遍历interceptors集合
    for (Interceptor interceptor : interceptors) {
      // 调用Interceptor.plugin()方法
      target = interceptor.plugin(target);
    }
    return target;
  }

  public void addInterceptor(Interceptor interceptor) {
    interceptors.add(interceptor);
  }

  public List<Interceptor> getInterceptors() {
    // 返回不可修改的List
    return Collections.unmodifiableList(interceptors);
  }

}
