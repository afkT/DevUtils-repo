
<h1 align="center">DevUtils-repo</h1>

<p align="center">
<a href="https://github.com/afkT"><img alt="Profile" src="https://img.shields.io/badge/GitHub-afkT-orange.svg"/></a>
<a href="https://github.com/afkT/DevUtils-repo/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
<a href="https://search.maven.org/search?q=io.github.afkt"><img alt="Version" src="https://img.shields.io/badge/Maven-Dev-5776E0.svg"/></a>
<a href="https://android-arsenal.com/api?level=14"><img alt="API" src="https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">
🐝 该项目是针对 <a href="https://github.com/afkT/DevUtils">DevUtils</a> 第三方库封装扩展、新技术 Demo 编写、大文件资源等迁移存储仓库。
</p>

<p align="center">
<b><a href="https://github.com/afkT/DevUtils/blob/master/README/android_standard.md">Android 规范</a></b>、
<b><a href="https://github.com/afkT/DevUtils/blob/master/README/java_standard.md">Java 规范</a></b>、
<b><a href="https://github.com/afkT/DevUtils/blob/master/README/git_standard.md">Git 规范</a></b>
</p>


## 作用

减少 `DevUtils` 仓库大小方便快速 clone，并让 `DevUtils` 项目**更加纯粹**只保留 Dev 系列开发库相关代码。

移除多余的第三方库、插件依赖配置，避免过多无关且繁杂配置影响快速理解项目，降低第三方库下载数量、编译运行 `DevUtils 演示 Demo App` 难度，使项目可更加快捷运行。


## 仓库优化前后对比

| before | after |
|:-:|:-:|
| ![][repositories_before] | ![][repositories_after] |

优化后仓库大小为 **12.6MB**，相较之前减少了 **56.5MB**。

### 如何彻底删除 Git 中的大文件、敏感数据

可以参考 GitHub 官方文档 [Removing sensitive data from a repository][Removing sensitive data from a repository]
或使用开源工具 [BFG Repo-Cleaner][BFG Repo-Cleaner]

下方以 `BFG Repo-Cleaner` 为例，演示优化 [DevUtils][DevUtils] 仓库空间大小步骤及命令。

1. 首先通过 [BFG Repo-Cleaner][BFG Repo-Cleaner] 下载 `bfg.jar` 后 clone 项目。

```gitexclude
git clone --mirror https://github.com/afkT/DevUtils.git
```

2. 接着执行删除文件、文件夹命令。

```gitexclude
java -jar /Users/afkT/bfg.jar --strip-blobs-bigger-than 1M /Users/afkT/DevUtils.git
```

上行命令表示删除大于 **1MB** 的所有文件，具体其他命令可以查看 `BFG API`

执行结果：

![deleted_files][deleted_files]

还有如删除文件夹

```gitexclude
java -jar /Users/afkT/bfg.jar --delete-folders interesting /Users/afkT/DevUtils.git
```

删除文件

```gitexclude
java -jar /Users/afkT/bfg.jar --delete-files image.zip /Users/afkT/DevUtils.git
```

3. 执行删除命令结束后，存在符合条件则调用 git gc。

```gitexclude
cd DevUtils.git
```

```gitexclude
git reflog expire --expire=now --all && git gc --prune=now --aggressive
```

4. 最后一步强制推送 git。

```gitexclude
git push --force
```


**注意事项：删除数据后，需要重新 clone 项目，因为 .git 隐藏文件存在历史数据，如果在原有 project.git 上面提交又会再次 push 上去。**

至此优化项目仓库大小，删除大文件、敏感数据操作结束。


## License

    Copyright 2022 afkT

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





[DevUtils]: https://github.com/afkT/DevUtils
[repositories_before]: https://github.com/afkT/DevUtils-repo/raw/main/art/git_delete/repositories_before.png
[repositories_after]: https://github.com/afkT/DevUtils-repo/raw/main/art/git_delete/repositories_after.png
[deleted_files]: https://github.com/afkT/DevUtils-repo/raw/main/art/git_delete/deleted_files.png
[BFG Repo-Cleaner]: https://rtyley.github.io/bfg-repo-cleaner
[Removing sensitive data from a repository]: https://docs.github.com/cn/authentication/keeping-your-account-and-data-secure/removing-sensitive-data-from-a-repository
