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
package org.apache.ibatis.cache.decorators;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.ibatis.cache.Cache;

/**
 * Lru (least recently used) cache decorator.
 *
 * @author Clinton Begin
 */
// lru：最近最少使用缓存
public class LruCache implements Cache {

  /**
   * 被装饰的底层Cache对象，一般传入的就是PerpetualCache
   */
  private final Cache delegate;

  /**
   * 类型对象，它是一个有序的HashMap，用于记录key最近的使用情况
   */
  private Map<Object, Object> keyMap;

  /**
   * 记录最少被使用的缓存项的key，也就是将要被淘汰的key
   */
  private Object eldestKey;

  public LruCache(Cache delegate) {
    this.delegate = delegate;
    // 设置默认缓存大小：1024，可以通过setSize更改
    setSize(1024);
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  public void setSize(final int size) {
    // 重新设置缓存大小时，会重置keyMap字段
    // accessOrder=true表示LinkedHashMap记录的顺序是按访问的顺序，天然的lru
    keyMap = new LinkedHashMap<Object, Object>(size, .75F, true) {
      private static final long serialVersionUID = 4267176411845948333L;

      // 调用put方法时，会调用该方法
      @Override
      protected boolean removeEldestEntry(Map.Entry<Object, Object> eldest) {
        boolean tooBig = size() > size;
        // 如果已到达缓存上限，则更新eldestKey，后面会删除eldestKey对应的缓存
        if (tooBig) {
          eldestKey = eldest.getKey();
        }
        return tooBig;
      }
    };
  }

  @Override
  public void putObject(Object key, Object value) {
    // 添加缓存
    delegate.putObject(key, value);
    // 检测并清理缓存
    cycleKeyList(key);
  }

  @Override
  public Object getObject(Object key) {
    keyMap.get(key); // touch
    return delegate.getObject(key);
  }

  @Override
  public Object removeObject(Object key) {
    keyMap.remove(key);
    return delegate.removeObject(key);
  }

  @Override
  public void clear() {
    delegate.clear();
    keyMap.clear();
  }

  private void cycleKeyList(Object key) {
    keyMap.put(key, key);
    // eldestKey != null，表示已经达到缓存上限
    if (eldestKey != null) {
      // 删除最久未使用的缓存项
      delegate.removeObject(eldestKey);
      eldestKey = null;
    }
  }

}
