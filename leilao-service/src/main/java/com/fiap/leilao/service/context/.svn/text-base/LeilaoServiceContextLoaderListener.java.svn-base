package com.fiap.leilao.service.context;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 
 * @author mtzcpd180
 * Listener do Spring para utilizacao do Vraptopr nos servicos rest
 */
public class LeilaoServiceContextLoaderListener extends ContextLoaderListener {
    
    /*
     * (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.context.ContextLoaderListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        LeilaoServiceContext.setContext(ContextLoader.getCurrentWebApplicationContext());
    }
    
}
