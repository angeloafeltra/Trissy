package org.example.action;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SerialComunication {
    private SerialPort sp;
    private boolean connection=false;
    private ArrayList<String> bufferComand;
    private boolean rec=false;

    public SerialComunication(){
        this.bufferComand=new ArrayList<>();
    }

    public String connection(String port, int baud) throws IOException, InterruptedException {
        if(!this.connection) {
            this.sp = SerialPort.getCommPort(port);
            this.sp.setComPortParameters(Integer.valueOf(baud), 8, 1, 0);
            this.sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

            if (this.sp.openPort()) {
                System.out.println("Port is open :)");
                Thread.sleep(2000);
                String msg="RichiedoConnessione\n";

                //Invio un messaggio tramite seriale
                this.sp.getOutputStream().write(msg.getBytes());
                this.sp.getOutputStream().flush();
                Thread.sleep(1000);

                //Leggo la risposta
                int bytesAvailable = this.sp.bytesAvailable();
                if (bytesAvailable <= 0) {
                    System.out.println("Nessuna risposta da arduino");
                    this.sp.closePort();
                    return "Connessione Fallita";
                }else{
                    String response="";
                    byte[] buffer=new byte[255];
                    int bytesRead = sp.readBytes(buffer, Math.min(buffer.length, bytesAvailable));
                    response = new String(buffer, 0, bytesRead);
                    System.out.println("Risposta di arduino: "+response);

                    if (response.equals("OK")){
                        this.connection = true;
                        return "Connessione Riuscita";
                    }else{
                        this.sp.closePort();
                        return "Connessione Fallita";
                    }
                }
            } else {
                System.out.println("Failed to open port :(");
                this.connection = false;
                return "Porta Occupata";
            }
        }else{
            return "Connessione gia attiva";
        }

    }


    public String releaseConnection(){

        if(this.connection) {
            if (this.sp.closePort()) {
                this.connection=false;
                return "Porta chisua";
            } else {
                return "Errore nella chiusura della porta";
            }
        }else{return "Nessuna connessione presente";}
    }


    public String sendMessage(String message) throws IOException {
        int value=-1;
        if(rec){ bufferComand.add(message);}

        String[] parts=message.split(":");

        if(parts[0].contains("Controller Base")){
            value=Integer.valueOf(parts[1]);
            //System.out.println(value);
            value=value+1000;
        }
        if(parts[0].contains("Controller Braccio")){
            value=Integer.valueOf(parts[1]);
            //System.out.println(value);
            value=value+2000;
        }
        if(parts[0].contains("Controller Avambraccio")){
            value=Integer.valueOf(parts[1]);
            //System.out.println(value);
            value=value+3000;
        }
        if(parts[0].contains("Magnete")){
            value=Integer.valueOf(parts[1]);
        }

        System.out.println("Messaggio Inviato: "+Integer.toString(value));
        if(this.connection && value>-1) {
            this.sp.getOutputStream().write((Integer.toString(value)+"\n").getBytes());
            this.sp.getOutputStream().flush();

            //Ottengo la risposta di arduino al messaggio
            return null;
        }else{
            return "Connettersi alla porta";
        }
    }

    public String[] getListPort(){
        SerialPort[] ports = SerialPort.getCommPorts();
        ArrayList<String> list = new ArrayList<String>();
        for (SerialPort port : ports) {
            list.add(port.getSystemPortName());
        }
        return list.toArray(new String[0]);
    }

    public void startRec(){
        this.rec=true;
    }

    public void stopRec(){
        this.rec=false;
    }

    public void playAutoMode() throws IOException, InterruptedException {
        for (String command:this.bufferComand){
            this.sendMessage(command);
            Thread.sleep(20);
        }

        ArrayList<String> reverse= (ArrayList<String>) this.bufferComand.clone();
        Collections.reverse(reverse);
        Thread.sleep(20);

        for (String command:reverse){
            this.sendMessage(command);
            Thread.sleep(20);
        }



    }

}
