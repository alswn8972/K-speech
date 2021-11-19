# MySQL 세팅

### Docker MySQL 컨테이너 구축

```bash
docker pull mysql:8.0.23
docker run --name [CONTAINER_NAME] -e MYSQL_ROOT_PASSWORD=passoword(사용자지정)-d -p 3306:3306 mysql:8.0.23 
docker start [CONTAINER_NAME]
```

### DB 생성

```bash
docker exec -it [CONTAINER_NAME] bash 
mysql -u root -p
비밀번호 입력
create database IF NOT EXISTS `Schema name` collate utf8mb4_general_ci;
SHOW DATABASES;
```

### SpringBoot MySQL 설정

```
# database
###
spring.jpa.hibernate.ddl-auto=<update or create>
###
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL57Dialect
spring.data.web.pageable.one-indexed-parameters=true
spring.datasource.url=jdbc:mysql://[EC2 퍼블릭 주소]\
  :3306/[Schema name]?useUnicode=true&characterEncoding=utf8&serverTimezone\
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.hikari.username=[username]
spring.datasource.hikari.password=[password]

```

spring.jpa.hibernate.ddl-auto=<update or create> 

- create  실행 시 기존 테이블내 데이터는 삭제되고 새로 만들어진다.
- update  실행 시 기존에 있던 데이터베이스 스키마 에서 변경 된 부분만 업데이트 된다.

### 한글 입력이 필요한 테이블 Character Set 변경

```sql
ALTER TABLE tableName
CONVERT TO CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```