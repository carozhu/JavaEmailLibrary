## Java Email

### Grade
```
compile 'com.carozhu:javaEmail:1.0.1'
```
### Maven
```
<dependency>
  <groupId>com.carozhu</groupId>
  <artifactId>javaEmail</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
### useage
```
final String sendEmail = "xx@qq.com";
final String sendEmailPswd = "xxx"; // 密码为且需要邮箱独立密码
final String recvEmail = "xxx@qq.com";
new Thread(new Runnable() {
    @Override
    public void run() {
        try {
            SendHelper.sendEmail("HELLO Email","world well",sendEmail,sendEmailPswd,recvEmail);
        } catch (MessagingException e) {
             e.printStackTrace();
        }
   }
}).start();
```