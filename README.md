# QuickHub
QuickHub是一款基于GithHub网站提供API开发的开源软件，界面简洁，操作便利，更新快。

[<img alt="Get it on Google Play" height="80" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png">](https://play.google.com/store/apps/details?id=com.androidwind.github)

#### 书籍源码参考请切换到book_source_code_feature分支。

#### master分支用于持续版本迭代。

![Image](https://github.com/ddnosh/githubusercontent/blob/master/image/QuickHub/icon_512.png)

#### APP采用泛型结构的MVVM架构设计，基于AAC（Android Architecture Components，包括LiveData、ViewModel、Room） + RxJava + Retrofit + Glide + ButterKnife + EventBus + RxPermissions构建。

## App截图  

| 最新 | 开源 | 侧边栏 |
|:-:|:-:|:-:|
| ![trends](https://github.com/ddnosh/githubusercontent/blob/master/image/QuickHub/p1.png?raw=true) | ![open](https://github.com/ddnosh/githubusercontent/blob/master/image/QuickHub/p2.png?raw=true) | ![drawer](https://github.com/ddnosh/githubusercontent/blob/master/image/QuickHub/p3.png?raw=true) |
    
## 版本变更记录

#### 2020-1-07    v1.0.0    初始版本
*  启动页
*  登录
*  首页-最新tab
*  首页-开源tab
*  首页-搜索tab
*  浏览记录
*  项目详情
*  抽屉栏-列表
*  抽屉栏-我的账户
*  抽屉栏-我的Star
*  抽屉栏-关于

## 使用方法
### book_source_code_feature分支中开发注意事项：
1. 在local.properties中新建两个字段：  
quickhub_client_id = ""  
quickhub_client_secret = ""  
#上面两个参数在Github账号下的Setting --> Developer settings --> OAuth Apps下创建一个OAuth App，系统会自动生成这两个参数。

### master分支中开发注意事项：
1. 首先在项目根目录下新建一个private.properties文件。
2. 其次按照如下格式新建内容：  
~~~ xml
KEYSTORE_FILE = 你的ketstore文件路径，windows下如C:\\android\\key\\myapp.jks
KEY_ALIAS = 你的ketstore别名，如myapp
KEYSTORE_PWD= 你的keystore密码，比如123456
KEY_PWD= 你的key密码，比如123456
#以上是创建keystore的时候自己填写的

quickhub_client_id = "github的client_id"
quickhub_client_secret = "github的client_secret"
#上面两个参数在Github账号下的Setting --> Developer settings --> OAuth Apps下创建一个OAuth App，系统会自动生成这两个参数。
~~~
