/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Carlos
 */
@ServerEndpoint("/proceso")
public class Proceso {

    private static Set<Session> sessiones = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        //System.out.println(session.getId() + "Ha abierto una conexion");
        try {
            sessiones.add(session);
            session.getBasicRemote().sendText("Conexion establecida");
        } catch (IOException ex) {
        }
    }

    @OnMessage
    public void onMessage(String mensaje, Session session) {
        //System.out.println("Mensaje" + session.getId() + mensaje);
        try {
            for (Session s : sessiones) {
                s.getBasicRemote().sendText(mensaje);
            }

        } catch (IOException ex) {
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessiones.remove(session);
        //System.out.println("Session " + session.getId() + " ha terminado");
    }

}
