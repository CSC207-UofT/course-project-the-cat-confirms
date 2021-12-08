# Specification

Our project is to develop a de-centralized chat room system.

In the system, every user can create her/his own chatroom. 

In a chatroom, everyone can chat with each other with messages like text, image and actions(like).

Users can share a chatroom with other users, by generating a QR code by the chatroom owner, and scanning by an enroller. 

The UI of the application is built by React, a popular web building framework. To be able to launch on an Android, the app will first launch an HTTP 
server backend and then load a local compiled webpage on the phone with the Android WebView.

## Once the app launches, a user can
* Enter her/his user name, which is used to be displayed in the chatroom as sender names
* when applicable, the app will try to load a previously stored user name from the local repository

## On the main page, a user can
* Create a chatroom with a given name
* Join a chatroom by entering a token or scanning a QR code

## In a chatroom, a user can
* Send a text/image message
* Share a chatroom by generating a token or a QR code
* Drop a room
