=package appserver.satellite;

import appserver.job.Job;
import appserver.comm.ConnectivityInfo;
import appserver.job.UnknownToolException;
import appserver.comm.Message;
import static appserver.comm.MessageTypes.JOB_REQUEST;
import static appserver.comm.MessageTypes.REGISTER_SATELLITE;
import appserver.job.Tool;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PropertyHandler;


/**
 * Class [Satellite] Instances of this class represent computing nodes that execute jobs by
 * calling the callback method of tool implementation, loading the tools code dynamically over a network
 * or locally, if a tool got executed before.
 *
 * @author Dr.-Ing. Wolf-Dieter Otte
 */
public class Satellite extends Thread {

    private ConnectivityInfo satelliteInfo = new ConnectivityInfo();
    private ConnectivityInfo serverInfo = new ConnectivityInfo();
    private HTTPClassLoader classLoader = null;
    private Hashtable toolsCache = null;

    public Satellite(String satellitePropertiesFile, String classLoaderPropertiesFile, String serverPropertiesFile) {

        // read the configuration information from the file name passed in
        // ---------------------------------------------------------------
        // ...
        private PropertyHandler satelliteConfig = null;
        private PropertyHandler classConfig = null;
        private PropertyHandler serverConfig = null;

        try{
          satelliteConfig = new PropertyHandler(satellitePropertiesFile);
          classConfig = new PropertyHandler(classLoaderPropertiesFile);
          serverConfig = new PropertyHandler(serverPropertiesFile);
        } catch (Exception e){
            e.printStackTrace();
            System.exit();
        }




        // create a socket info object that will be sent to the server
        // ...


        // get connectivity information of the server
        // ...
        String serverHost = serverConfig.getProperty("HOST");
        String ServerPortString = serverConfig.getProperty("PORT");



        // create class loader
        // -------------------
        // ...
        private void initOperationsLoader() {
            String host = null;
            String portString = null;
            if ((host != null) && (portString != null)) {
                try {
                    classLoader = new HTTPClassLoader(host, Integer.parseInt(portString));
                } catch (NumberFormatException nfe) {
                    System.err.println("Wrong Portnumber, using Defaults");
                }
            } else {
                System.err.println("configuration data incomplete, using Defaults");
            }

            if (classLoader == null) {
                System.err.println("Could not create HTTPClassLoader, exiting ...");
                System.exit(1);
            }
        }

        // read class loader config
        // ... Read above



        // get class loader connectivity properties and create class loader
        // ... Pending, need eample properties file
        String classHost = classConfig.getProperty("HOST");
        String classPortString = classConfig.getProperty("PORT");

        initOperationsLoader(host, portString);


        // create tools cache
        // -------------------
        // ... Separate Function Done

    }

    @Override
    public void run() {

        // register this satellite with the SatelliteManager on the server
        // ---------------------------------------------------------------
        // ...


        // create server socket
        // ---------------------------------------------------------------
        // ...
        try{
          serverSocket = new ServerSocket(port);

          while(true){
            System.out.println("Waiting for connections on Port #" + port);
            socket  = serverSocket.accept();
            System.out.println("A connection to a client is established!");
            //add Try-catch
            (new SatelliteThread(socket, this)).start();
          }

        }catch(IOException ioe){
          system.err.println("IOException" + ioe,getMessage());
          ioe.printStackTrace();
        }



        // start taking job requests in a server loop
        // ---------------------------------------------------------------
        // ...

    }

    // inner helper class that is instanciated in above server loop and processes job requests
    private class SatelliteThread extends Thread {

        Satellite satellite = null;
        Socket jobRequest = null;
        ObjectInputStream readFromServer = null;
        ObjectOutputStream writeToServer = null;
        Message message = null;

        SatelliteThread(Socket jobRequest, Satellite satellite) {
            this.jobRequest = jobRequest;
            this.satellite = satellite;
        }

        @Override
        public void run() {
            // setting up object streams
            // ...
            readFromServer = new ObjectInputStream(jobrequest);
            writeToServer = new ObjectOutputStream(jobrequest);

            // reading message
            // ...
            message = (Message) readFromServer.readObject();




            // processing message
            switch (message.getType()) {
                case JOB_REQUEST:
                    Job job = (Job) message.getContent();
                    //job.getToolName
                    //Make Object that dictates parameters and class name inside content object
                    //HELP
                    //get string toolName
                    //get parameters
                    Tool tool = getToolObject(toolName);
                    Object result = tool.go(toolParameters);
                    //call toolName with parameters
                    //send results back (writeToServer) result.writeToServer

                    break;

                default:
                    System.err.println("[SatelliteThread.run] Warning: Message type not implemented");
            }
        }
    }

    /**
     * Aux method to get a tool object, given the fully qualified class string
     *
     */
    public Tool getToolObject(String toolClassString) throws UnknownToolException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Tool toolObject = null;

        // ...
        if ((toolObject = (Tool) toolsCache.get(toolClassString)) == null) {
            //String operationClassString = configuration.getProperty(operationString);
            System.out.println("\nTool's Class: " + toolClassString);
            if (toolClassString == null) {
                throw new UnknownOperationException();
            }

            Class toolClass = classLoader.loadClass(toolClassString);
            toolObject = (Tool) toolClass.newInstance();
            toolsCache.put(toolClassString, toolObject);
        } else {
            System.out.println("Tool: \"" + toolClassString + "\" already in Cache");
        }

        return toolObject;
    }

    public static void main(String[] args) {
        // start a satellite
        Satellite satellite = new Satellite(args[0], args[1], args[2]);
        satellite.run();

        //(new Satellite("Satellite.Earth.properties", "WebServer.properties", "Server.properties")).start();
        //(new Satellite("Satellite.Venus.properties", "WebServer.properties")).start();
        //(new Satellite("Satellite.Mercury.properties", "WebServer.properties")).start();
    }
}
