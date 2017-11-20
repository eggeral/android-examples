package com.example;

import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.servlet.api.ServletInfo;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

public class Server {

    public static void main(String[] args) throws ServletException {
        System.setProperty("log4j.configurationFile", "log4j2.xml");

        int port = 8080;

        PathHandler rootPathHandler = new PathHandler();

        Undertow server = Undertow.builder()
                .addHttpListener(port, "0.0.0.0") // 0.0.0.0 bind to all interfaces
                .setHandler(rootPathHandler)
                .build();
        server.start();

        Application application = new RestApplication();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(application);

        ServletInfo resteasyServlet = Servlets.servlet("ResteasyServlet", HttpServlet30Dispatcher.class)
                .setAsyncSupported(true)
                .setLoadOnStartup(1)
                .addMapping("/");

        DeploymentInfo deploymentInfo = new DeploymentInfo()
                .addServletContextAttribute(ResteasyDeployment.class.getName(), deployment)
                .addServlet(resteasyServlet)
                .setDeploymentName("RestServices")
                .setContextPath("/rest")
                .setClassLoader(Undertow.class.getClassLoader());

        DeploymentManager deploymentManager = ServletContainer.Factory.newInstance().addDeployment(deploymentInfo);
        deploymentManager.deploy();

        rootPathHandler.addPrefixPath(deploymentInfo.getContextPath(), deploymentManager.start());

    }

}
