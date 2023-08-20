package com.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/**
 * Web Server
 * @author Neeraj Mukati
 */
public class App 
{
    public static void main( String[] args ) throws LifecycleException
    {
        startTomcat();   
    }
    private static void startTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath(); 
        Context context = tomcat.addContext(contextPath, docBase);
        
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);
        tomcat.setPort(Integer.valueOf(webPort));
        tomcat.addServlet(contextPath, "HelloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello", "HelloServlet");
        tomcat.start();
    }
}
