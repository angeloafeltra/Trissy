package org.example.comunication;


import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialComunication {
    private SerialPort sp;
    private boolean connection = false;
    private Logger logger;


    public SerialComunication() {
        this.logger = Logger.getLogger(SerialComunication.class.getName());
    }


    public boolean startConnection(String port, int baud) throws InterruptedException, IOException {
        this.sp = SerialPort.getCommPort(port);
        this.sp.setComPortParameters(baud, 8, 1, 0);
        this.sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (this.sp.openPort()){
            this.logger.log(Level.INFO,"Porta aperta");
            Thread.sleep(2000);

            this.connection=true;
            this.sendMessage("RichiedoConnessione");
            Thread.sleep(1000);
            String response=this.readResponse();
            if(response!=null && response.equals("OK")){
                return true;
            }else{
                this.logger.log(Level.INFO,"Connessione Fallita");
                this.connection=false;
                this.sp.closePort();
                return false;
            }
        }else{
            this.logger.log(Level.INFO,"Porta occupata");
            return false;
        }

    }

    public boolean disconnect(){
        if(this.connection){
            if(this.sp.closePort()){
                this.connection=false;
                this.logger.log(Level.INFO,"Porta chiusa");
                return true;
            }else{
                this.logger.log(Level.INFO,"Errore nella chiusura della porta");
                return false;
            }
        }else{
            this.logger.log(Level.INFO,"Nessuna connessione presente");
            return false;
        }


    }

    public boolean isConnection() {return this.connection;}


    public void sendMessage(String message) throws IOException {

        this.logger.log(Level.INFO,"Messaggio da inviare: {0}",message);
        if(this.connection) {
            String messaggioCodificato=this.codificatore(message);
            this.logger.log(Level.INFO,"Messaggio codificato da inviare: {0}",messaggioCodificato);
            this.sp.getOutputStream().write((messaggioCodificato+"\n").getBytes());
            this.sp.getOutputStream().flush();
        }
    }

    public String readResponse() {

        int bytesAvailable = this.sp.bytesAvailable();
        if (bytesAvailable > 0) {
            String response;
            byte[] buffer = new byte[255];
            int bytesRead = sp.readBytes(buffer, Math.min(buffer.length, bytesAvailable));
            response = new String(buffer, 0, bytesRead);
            this.logger.log(Level.INFO,"Messaggio letto: {0}",response);
            return response;
        } else {
            return null;
        }

    }

    private String codificatore(String messaggio){

        if (messaggio.equals("RichiedoConnessione")) return messaggio;
        if (messaggio.contains("Controller Base")){
            String[] parts=messaggio.split(":");
            int value=Integer.parseInt(parts[1]);
            value=value+1000;
            return Integer.toString(value);
        }
        if (messaggio.contains("Controller Braccio")){
            String[] parts=messaggio.split(":");
            int value=Integer.parseInt(parts[1]);
            value=value+2000;
            return Integer.toString(value);
        }
        if (messaggio.contains("Controller Avambraccio")){
            String[] parts=messaggio.split(":");
            int value=Integer.parseInt(parts[1]);
            value=value+3000;
            return Integer.toString(value);
        }
        if (messaggio.equals("Magnete On")) return "011";
        if (messaggio.equals("Magnete Off")) return "010";
        if (messaggio.equals("Tris Mode")) return "111";
        if (messaggio.equals("Clear")) return "000";
        if (messaggio.contains("Tris:")) {
            String[] parts = messaggio.split(":");
            int value = Integer.parseInt(parts[1]);
            return Integer.toString(value);
        }

        return null;
    }

    public String[] getListPort(){
        SerialPort[] ports = SerialPort.getCommPorts();
        ArrayList<String> list = new ArrayList<>();
        for (SerialPort port : ports) {
            list.add(port.getSystemPortName());
        }
        return list.toArray(new String[0]);
    }



}
