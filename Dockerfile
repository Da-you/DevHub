#도커이미지 생성 시 기본으로 사용 베이스 이미지 설정(jdk 버전등)
FROM openjdk:21-jdk
#컨테이너 내에서의 작업 디렉토리 설정
WORKDIR /spring-boot
#호스트 시스템의 설정 경로에 있는 .jar 파일을 도커 컨테이너의 /spring-boot/  경로에 app.jar 파일로 복사
COPY build/libs/*SNAPSHOT.jar app.jar

ENV TZ=Asiz/Seoul
#도커 실행 시 java -jar /spring-boot/app.jar 명령어가 실행 즉 해당 명령어는 spring boot 애플리케이션 실행
ENTRYPOINT ["java","-jar", "/spring-boot/app.jar"]