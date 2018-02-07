## JAVA EMAIL TOOLS
#### simple useage
```
//send Email
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