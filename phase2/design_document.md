# Design Document

## Clean Architecture
![image](https://user-images.githubusercontent.com/43196707/145178007-4bb7d341-295f-4540-83bb-8ae79150db70.png)

## CRC cards
![image](https://user-images.githubusercontent.com/43196707/145180955-e1b5cfe4-4625-4e69-8358-ddd0c2d04097.png)


## APIs
| Path               | Parameters                   | Action                  |
| ------------------ | ---------------------------- | ----------------------- |
| /owner             | None                         | get owner info          |
| /changeOwnerName   | newName: new name to change  | change owner name       |
| /chatroom_create   | roomName: new room's name    | create a new chat room  |
| /chatroom_view     | roomId: the chatroom to view<br>timestamp: only message after this timestamp will be returned  | view chatroom message since timestamp |
| /chatroom_send     | roomId: the chatroom to send<br>senderId: who's sending the message<br>RequestBody: the message | send message to some chatroom        |
| /enroll            | roomId: the chatroom to join<br>RequestBody: enroller info  | enroll the given user to some room |
