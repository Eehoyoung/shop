package com.example.shop.chatFn;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ServerEndpoint("/chatserver")
public class chatServer {

    private static final List<Session> list = new ArrayList<>();

    private void print() {
        System.out.printf("[%tT] %s\n", Calendar.getInstance(), "클라이언트 연결");
    }

    @OnOpen
    public void handleOpen(Session session) {
        print();
        list.add(session);
    }

    @OnMessage
    public void handleMessage(String msg, Session session) {


        int index = msg.indexOf("#", 2);
        String no = msg.substring(0, 1);
        String user = msg.substring(2, index);
        String txt = msg.substring(index + 1);

        switch (no) {
            case "1":
                for (Session s : list) {
                    if (s != session) {
                        try {
                            s.getBasicRemote().sendText("1#" + user + "#");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "2":
                // 누군가 메세지를 전송
                for (Session s : list) {

                    if (s != session) { // 현재 접속자가 아닌 나머지 사람들
                        try {
                            s.getBasicRemote().sendText("2#" + user + ":" + txt);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case "3":
                // 누군가 접속 > 3#아무개
                for (Session s : list) {
                    if (s != session) { // 현재 접속자가 아닌 나머지 사람들
                        try {
                            s.getBasicRemote().sendText("3#" + user + "#");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                list.remove(session);
                break;
        }
    }

    @OnClose
    public void handleClose() {
    }

    @OnError
    public void handleError(Throwable t) {
    }

}
