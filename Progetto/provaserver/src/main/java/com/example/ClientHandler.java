package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket s;
    private List<ClientHandler> clients;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String hostname = null;
    private MessagioMaranzone msg=null;
    private String sessione = null;

    public ClientHandler(Socket s, List<ClientHandler> clients) {
        this.s = s;
        this.clients = clients;

        try {
             in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
   
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

    //crezioni variabili    
        boolean ciclo = true;
        Boolean ciclop= true;

        try {   
        //inserimento del hostname
            out.println("inserire il tuo hostname ");
            hostname = in.readLine();
            System.out.println("Nuovo utente con nome " + hostname);

        //ricezzione tabella se vuole essere publico o privato la connesione
                
                while (ciclop) {
                out.println(" Vuoi comunicare in pubblico o privato ?");
                sessione = in.readLine();
                System.out.println(sessione);
                //sessione privata
                if (sessione.equalsIgnoreCase("privato")){
                    out.println("scrivi il nome della persona con cui vuoi chattare se vuoi chiudere la chat digita (esci da chat)");
                    String destinatario=in.readLine(); 
                    out.println("scrivi il messagio");
                    while(ciclo){
                    String messaggio=in.readLine(); 
                    if(messaggio.equals("esci da chat")){ciclo=false;break;}
                    msg=new MessagioMaranzone(hostname, destinatario, messaggio,sessione);
                    invioSingolo(msg);
                   }


                }//sessione publica
                else if (sessione.equalsIgnoreCase("publico")) {
                    out.println("se vuoi uscire scrivi:esci da chat");
                    out.println("inserire cosa vuore dire hai fra");

                   while(ciclo){
                        String messagio=in.readLine(); 
                        if(messagio.equalsIgnoreCase("esci da chat")){ciclo=false;}
                        msg=new MessagioMaranzone(hostname, messagio, sessione);
                        sendToAll(msg);
                    
                    }    
                   
                }//uscita dalla sessione
                else if (sessione.equalsIgnoreCase("uscire")) {
                    
                    out.println("Addio ti ho voluto pene mon amour");
                    sendToAll("@");
                    ciclo = false;
                }//messagio non valido
                else{out.println("Non è valido questo comando");}
            }
        } catch (Exception e) {
       
        }
    }



    
    private void sendToAll(String msg) {
        for (ClientHandler client : clients) {
            System.out.println(client.getName());
            client.out.println(msg);
            
        }
    }
    private void sendToAll(MessagioMaranzone maranzone) {

        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getHostname().equalsIgnoreCase(maranzone.getMitente()) ){}else if (clients.get(i).getSessione().equals("privato")){}
            else{
                clients.get(i).out.println("Gruppo"+"("+maranzone.getMitente()+"):"+maranzone.getMsg());
            }
        }
    }
    private void invioSingolo(MessagioMaranzone maranzone) {
        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getHostname().equalsIgnoreCase(maranzone.getDestinatario())){
                clients.get(i).out.println(maranzone.getMitente()+":"+maranzone.getMsg());
            }
        }
    }


    public Socket getS() {
        return s;
    }

    public String getHostname() {
        return hostname;
    }

    public String getSessione() {
        return sessione;
    }



}
