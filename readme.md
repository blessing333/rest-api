목표 
최대한 확장과 변경에 쉽도록 작성하자

요구사항
한번에 여러개의 아이템을 주문할 수 있다.
하나의 아이템은 단 하나의 카테고리만을 가진다.
주문은 하고나서 수정할 수 없다. 취소하고 다시 해야한다.
주문된 아이템은 주문 수량만큼 재고가 감소해야한다.

설계
도메인 서비스에 전달되는 데이터는 반드시 어플리케이션 서비스 영역에서 검증이 이루어져야한다.

DB
하나의 아이템은 하나의 카테고리를 가진다
하나의 주문은 여러개의 주문아이템을 가진다

주문 로직
1. 사용자가 웹에서 마음에 드는 상품을 추가
2. 주문하기 버튼 누름
3. 주문자 id, item id 배열을 전송 



힘들었던 점
1. 레포지토리 테스트시 외래키 제약 관계 때문에 너무 힘들었다.


궁금한점
1. 기본키를 복합키를 구성하여 얻을수 있는 장점?
2. 프론트단에서 주문을 요청할 때 Item 아이디만 보내줄지 아니면 모든 필드를 다 풀어서 전송할지
3. 자바의 시리얼라이즈는 언제 사용하는가
4. Optional vs UncheckedException
5. Order, OrderItem, Item