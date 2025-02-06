FROM gradle:8.11.1-jdk17 as build

# 소스코드를 복사 할 작업 디렉토리를 생성
WORKDIR /myapp

# 호스트 머신에 소스코드를 이미지 작업 디렉토리로 복사
COPY . /myapp

# 이전 빌드에서 생성된 모든 build/ 디렉토리 내용을 삭제, 새롭게 빌드
# 프로젝트를 빌드
# --no-daemon 은 데몬을 이용하지 않고 빌드
# gradle 은 설치되어 있는 gradle 을 이용해서 빌드, gradlew 는 프로젝트에 포함된 gradle 을 이용
# CICD 에서는 gradlew 를 이용해서 작업
# -x test 는 test 를 제외하고 작업
# gradlew 를 실행할 수 있는 권한을 추가
RUN chmod +x gradlew
RUN gradlew clean build --no-daemon -x test

# 자바를 실행하기 위한 작업
FROM openjdk:17-alpine

WORKDIR /myapp

# 프로젝트 빌드로 생성된 jar 파일을 런타임 이미지로 복사
COPY --from=build /myapp/build/libs/*.jar /myapp/orderservice.jar
EXPOSE 9200
ENTRYPOINT ["java","-jar","/basic_test.jar"]