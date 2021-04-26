#  Eclipse에 MariaDB 설치하기

1. Data source Explorer window를 열어준다

![image-20210425203822169](images/image-20210425203822169.png)



### 1-1 Data source explorer가 없을경우

Help -> Install New Software -> 화살표아이콘 클릭

![image-20210425203710285](images/image-20210425203710285.png)



~ /relese/<날짜> 형태의 링크 선택

Database Development 체크 후 Next 클릭 후 계속 진행

(사진캡처 당시 설치되어있어서 Next 비활성화)

![image-20210425204817441](images/image-20210425204817441.png)



관련된 블로그 post 참조

https://blog.naver.com/PostView.nhn?blogId=gingsero&logNo=221026840250&proxyReferer=https:%2F%2Fwww.google.com%2F



2. Database Connections 에서 New 선택

![image-20210425205123917](images/image-20210425205123917.png)



3. mysql 선택 후 Name 입력

![image-20210425205207965](images/image-20210425205207965.png)



4. Driver 추가

![image-20210425205325676](images/image-20210425205325676.png)



driver template선택 후 driver name 설정

![image-20210425205513984](images/image-20210425205513984.png)



기존의 mysql-connector jar 파일을 삭제(remove)하고 maria connector jar 추가

![image-20210425205609247](images/image-20210425205609247.png)



​	connector 파일 받는곳

​	https://downloads.mariadb.org/connector-java/

![image-20210425205728921](images/image-20210425205728921.png)

![image-20210425205745211](images/image-20210425205745211.png)



properties 수정

![image-20210425205911283](images/image-20210425205911283.png)

URL = jdbc:mariadb://localhost:3306/\<dbname\>

Driver Class = org.mariadb.jdbc.Driver

두가지는 고정, 나머지는 직접수정



생성한 Driver를 선택하고 DB에 대한 properties 수정

![image-20210425210025942](images/image-20210425210025942.png)



정상적으로 만들어지면 ping test가 성공함

![image-20210425210105064](images/image-20210425210105064.png)



![image-20210425210130042](images/image-20210425210130042.png)







