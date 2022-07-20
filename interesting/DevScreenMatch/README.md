### 默认生成存储到该项目 res 目录下 ( 可以自行更改 )

- [GeneratePxValueFiles][GeneratePxValueFiles] - 生成对应的 x、y 适配文件

> values-HeightxWidth -> values-1334x750

- [GenerateDPValueFiles][GenerateDPValueFiles] - 需要先在该项目目录下 res/values 文件夹内创建适配的 dimens.xml 文件，并且修改基准 DP

> values-wXXXdp => values-w820dp

### 具体参考

- [Android 屏幕适配方案][Android 屏幕适配方案]
  
- [流行设备尺寸信息][流行设备尺寸信息]





[GeneratePxValueFiles]: https://github.com/afkT/DevUtils-repo/blob/main/interesting/DevScreenMatch/src/main/java/dev/screen/px/GeneratePxValueFiles.java
[GenerateDPValueFiles]: https://github.com/afkT/DevUtils-repo/blob/main/interesting/DevScreenMatch/src/main/java/dev/screen/dp/GenerateDPValueFiles.java
[流行设备尺寸信息]: https://screensiz.es/droid-razr
[Android 屏幕适配方案]: https://blog.csdn.net/lmj623565791/article/details/45460089