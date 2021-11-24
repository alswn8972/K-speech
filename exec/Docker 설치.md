# Docker 설치

### 이 문서는 AWS EC2에 프로젝트 `배포` 시 사용하는 Docker 를 설치하기 위한 방법입니다.

- 우분투 버전 : AWS EC2 Ubuntu 20.04 LTS (GNU/Linux 5.4.0-1018-aws x86_64)

```bash
# 필수 패키지 설치
sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
```

```bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

```bash
# docker repository 등록

add-apt-repository \
"deb [arch=amd64] https://download.docker.com/linux/ubuntu \
$(lsb_release -cs) \
stable"
```

```bash
# docker 설치
sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io
```

```bash
# docker 설치 확인
docker -v
```

```bash
# 도커 그룹에 유저 등록해서 일반 유저도 도커 사용하기
sudo usermod -aG docker $USER

# docker 재시작
sudo service docker restart
```