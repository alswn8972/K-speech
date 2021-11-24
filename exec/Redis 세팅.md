# Redis 세팅

### Docker Redis 컨테이너 구축

```bash
docker pull redis:6.2.6
wget http://download.redis.io/redis-stable/redis.conf -O /home/ubuntu/redis/redis.conf
redis.conf -> modify port & setting password
docker run -d --name redis -p [modify port]:[modify port] -v /home/ubuntu/redis.conf:/usr/local/etc/redis/redis.conf --network test-net redis redis-server /usr/local/etc/redis/redis.conf --appendonly yes
```

### DB 생성

```bash
docker exec -it [CONTAINER_NAME] bash 
redis-cli -p [modify port] --raw
auth 비밀번호 입력
get keys*
```

### SpringBoot MySQL 설정

```
# database
spring.redis.host=[EC2 퍼블릭 주소]
spring.redis.port=[modify port]
```
