package com.fiap.leilao.service.context;

import org.springframework.context.ApplicationContext;

/**
 * 
 * @author mtzcpd180
 * Context do Spring para geolocalizacao
 */
public class LeilaoServiceContext {
	
    private static ApplicationContext applicationContext;

    public static void setContext(ApplicationContext context) {
        applicationContext = context;
    }
    
    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
