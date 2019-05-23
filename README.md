# Colini Attach API


#### Spring Boot 생성

* [x] JPA 설정

* [x] MySQL 설정

* [x] Web 설정


#### DB 생성

* [x] MySQL on Docker

* [x] Redis on Docker (Single)

  * Cluster 구성은 나중에

* [x] [DB Modeling](https://github.com/team-friday/friday_colini_attach_api/wiki/DB-%EB%AA%A8%EB%8D%B8%EB%A7%81)

* [x] Create SQL


#### 준비

* [x] [요구사항 정리](https://github.com/team-friday/friday_colini_attach_api/wiki/%EC%9A%94%EA%B5%AC%EC%82%AC%ED%95%AD-%EC%A0%95%EB%A6%AC)

* [x] [패키지 구성](https://github.com/team-friday/friday_colini_attach_api/wiki/%ED%8C%A8%ED%82%A4%EC%A7%80-%EA%B5%AC%EC%84%B1)

* [x] [패키지 별 설계 정리](https://github.com/team-friday/friday_colini_attach_api/wiki/%ED%8C%A8%ED%82%A4%EC%A7%80-%EA%B5%AC%EC%84%B1)

* [ ] API 문서

    * [ ] Rest Docs


#### 기능 구현

  * [ ] 파일 업로드
  
    * [x] Local
    
    * [x] Amazon S3
    
    * [X] 파일 크기 검증
      
    * [ ] 파일 갯수 검증
  
  * [ ] 파일 다운로드
  
    * [x] Local
      
    * [x] Amazon S3
  
    * [ ] 파일 다운로드 시간 만료
    
  * [x] 다운로드 조회수 증가
  
  * [ ] 권한 확인
  
    * [ ] JWT 검증 
  
    * [ ] 업로드 권한
    
    * [ ] 다운로드 권한
  
  * [ ] 통계
  
  * [ ] 데이터베이스
    
    * [ ] 인덱싱
    
    