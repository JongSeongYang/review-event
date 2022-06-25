# Review Event

### 개발 환경
* Java 11
* SpringBoot 2.6.8
* Gradle
* JPA
* H2 database(embedded)
* Swagger

### Setting
* Server port : 8081

### Swagger
http://localhost:8081/swagger-ui/index.html

### DB
* Address : http://localhost:8081/h2-console
* username : sa
* password : 

### DB 구조

* `User` 1:1 `Point`
* `Point` 1:N `Point_History`
* `review_id` 와 `place_id`로 `ph_index` 인덱스 생성
<br/><br/>
![DB](https://user-images.githubusercontent.com/60866755/175763753-3c846a66-6b5c-4d12-bbde-0d7ee31c98b5.png)


### Exception Code
> Exceptoin Response
 ```
 {
  "code": Integer,
  "codeName": "String",
  "message": "String.",
  "timestamp": "Date"
}
 ```
> Auth
* `10001` : EMAIL_DUPLICATED
* `10002` : USER_CREATE_FAIL
* `10003` : USER_NOT_FOUND
> History
* `20001` : HISTORY_NOT_FOUND
* `20002` : HISTORY_EXIST
* `20003` : WRONG_ACTION
> Internal
* `500` : INTERNAL_ERROR
<br/><br/>
### 개발 컨셉
> - `Point_Histroy` 에서 `Event(Review)`에 따른 포인트 지급 내역을 관리
> - `User` 와 `Point`를 분리하여 관리(지갑처럼)
> - 취소 및 삭제를 `Deleted_time`으로 관리
> - Custom Exception Response를 구현하여 Exception 처리


> User
1. User 등록 `Post` `/users` 
* RequestBody
```
{
  "email": "string",
}
```
* Response(success)
```
{
  "message": User UUID,
  "result": true
}
```
* Response(Exception)
  * 이메일 중복 등록 -> `EMAIL_DUPLICATED`
  * 사용자 등록 실패 -> `USER_CREATE_FAIL`
<br/><br/>

2. User Point 조회 `Get` `/user/points` 
* Parameters
```
String userId;
```
* Response(success)
```
{
  "points": Point,
  "userId": User UUID
}
```
* Response(Exception)
  * User 조회 실패 -> `USER_NOT_FOUND`

<br/><br/>
3. Event `Post` `/Events` 
>> 삭제 요청이 발생하면 `deletedTime` 필드를 `null`에서 현재 시간(UTC)으로 변경
* Response(success)
```
{
  "message": ActionType,
  "result": true
}
```
* Response(Exception)
  * History 조회 실패 -> `HISTORY_NOT_FOUND`
  * 이미 존재하는 History 일 경우 -> `HISTORY_EXIST`
  * 잘못된 Action 일 경우 -> WRONG_ACTION`

<br/><br/>
<br/><br/>
