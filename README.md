# 원두 판매 REST API
**SpringBoot와 JPA를 활용한 REST API 학습프로젝트 입니다.**

## 패키지 구조

    ㄴ📂 domain
      ㄴ📂 order
        - OrderService
        - Order
      ㄴ📂 orderItem
        - OrderItemService
        - OrderItem
      ㄴ📂 product
        - ProductService
        - Product

    ㄴ📂 web
      ㄴ📂 order
        ㄴ📂 form
        ㄴ📂 controller
      ㄴ📂 product
        ㄴ📂 form
        ㄴ📂 controller


## 프로젝트 요구사항
- 기술 스택은 자유롭게 선택합니다.
- HTTP 메서드 PUT 를 이용해 Update, DELETE 를 이용해 Delete 기능을 구현합니다.
  - PUT : 해당하는 id에 해당하는 데이터를 갱신하는 기능을 구현합니다.
  - DELETE : 해당하는 id에 해당하는 데이터를 삭제하는 기능을 구현합니다.
- 새로운 메뉴를 추가하는 POST 영역에서 id가 4로 고정되어있는 문제를 해결합니다.
- SQL과 ORM 중 하나를 선택하여 데이터 베이스를 구현하여 제작합니다.
- 구현한 데이터베이스 연동을 구현합니다.

## 사용 기술
<p>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring jpa-색상?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
<figcpation>
<p>

## 요구사항 기능 구현
- 원두,주문,주문상품 각각의 CRUD
- 이메일을 통한 주문 조회가능
- 각각의 주문ID를 통해 주문상품 조회가능



## 요구사항 외 추가 적용 기능
**Bean Validation 적용**      

이론으로 공부했었던 Bean Validation을 적용하여 클라이언트에서의 입력값을 검증하였습니다.
- OrderSaveForm<br>
  <img width="228" alt="스크린샷 2024-09-09 오후 7 01 41" src="https://github.com/user-attachments/assets/b6eae288-72c1-45da-9225-1aebb0f3fb3c">

- OrderEditForm<br>
  <img width="249" alt="스크린샷 2024-09-09 오후 7 01 50" src="https://github.com/user-attachments/assets/e4fb70f4-e50c-47d8-ad89-4494c2010d25">

- OrderController<br>
  <img width="802" alt="스크린샷 2024-09-09 오후 7 03 16" src="https://github.com/user-attachments/assets/348fa51b-b641-4623-b837-6110d0a81af1">

Order, OrderItem, Product 에 대해 각각 저장/수정을 분리하여 검증값을 지정해주었습니다. 이렇게 하나의 Entity에 관한 저장/수정 검증을 분리함으로써 가독성과 유지보수성을 높이고자 했습니다.





 
## 문제상황 & 해결

**문제상황**
- Order Entity 문제발생 부분<br>
    <img width="559" alt="스크린샷 2024-09-09 오후 5 32 10" src="https://github.com/user-attachments/assets/b21ef2c8-829e-4993-b7a0-322360e7a65b">
<br>

- OrderItem Entity 문제발생 부분<br>
    <img width="366" alt="스크린샷 2024-09-09 오후 5 35 14" src="https://github.com/user-attachments/assets/503b7671-d32d-4107-841a-dd11eb353b4f">


OrderItem Entity에서 Order Entity를 참조하고 있기 때문에 `List<OrderItem>` 부분에서 순환참조가 발생하였습니다.
<br><br>

**문제해결**
- OrderItemResponseDTO<br>
    <img width="272" alt="스크린샷 2024-09-09 오후 6 47 00" src="https://github.com/user-attachments/assets/931fd6b1-12c6-406f-ab49-958227c31df9">
<br>

- OrderResponseDTO<br>
    <img width="836" alt="스크린샷 2024-09-09 오후 6 46 42" src="https://github.com/user-attachments/assets/ad36f780-e42e-43f7-8ca6-5091d6ebd717">

클라이언트에 Entity를 직접 반환하는 것이 아닌 DTO를 활용하여 순환참조 문제를 해결하였습니다.

## 느낀점
- JPA를 활용한 첫 프로젝트 조금 헤맸지만 그만큼 얻어가는 것도 많고 공부해야 할 것도 많다는 걸 깨달았습니다..!
- swagger 세부 설정에 대해서 더 공부 해보기!
- 좋은 아키텍처란 무엇인가 고민하고 공부하기!




