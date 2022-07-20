# About

该目录主要存储 DevUtils project 全部 local lib module，封装快捷使用的工具类及 API 方法调用，隐藏内部逻辑判断对外提供便捷辅助类、统一回调类、Bean、Event 以及
Engine 兼容框架等

## 目录结构

```
- lib                                            | 根目录
   - LocalModules                                | 本地 Module lib ( 非发布库 )
      - DevBase2                                 | Base 基础代码 ( 非基类库 )
      - DevComponent                             | 【100% Kotlin 实现 Android 项目组件化示例代码】
         - lib_utils                             | /core/libs/lib_utils
      - DevOther                                 | 第三方库封装、以及部分特殊工具类等, 方便 copy 封装类使用
```

