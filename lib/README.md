# About

该目录主要存储 DevUtils project 全部 local lib module，封装快捷使用的工具类及 API 方法调用，隐藏内部逻辑判断对外提供便捷辅助类、统一回调类、Bean、Event 以及
Engine 兼容框架等

## 目录结构

```
- lib                                            | 根目录
   - LocalModules                                | 本地 Module lib ( 非发布库 )
      - DevBaseView                              | 通用基础 View 封装 ( 非基类库 )
      - DevComponent                             | 【100% Kotlin 实现 Android 项目组件化示例代码】
         - lib_utils                             | /core/libs/lib_utils
      - DevOther                                 | 功能、工具类二次封装, 直接 copy 使用【 部分迁移至 DevUtils-repo 】
```

