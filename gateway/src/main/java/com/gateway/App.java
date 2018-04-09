package com.gateway;

import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * Hello world!
 *
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableZuulProxy
public class App 
{
	 @Autowired
	  private Environment env;
	 
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
    
    @Autowired
    private DiscoveryClient discovery;

    @Bean
    public ServiceRouteMapper serviceRouteMapper() {
      return new ServiceRouteMapper() {
        @Override
        public String apply(String serviceId) {
          return StringUtils.replaceChars(serviceId, '-', '/');
        }
      };
    }

//    @Bean
//    public GateWayFilter filter() {
//      String url = env.getProperty("fae.filter", "");
//      return new GateWayFilter(url);
//    }
//    
//    @Bean
//    public TokenFilter oauthFilter() {
////      String url = env.getProperty("fae.filter", "");
//      return new TokenFilter();
//    }

    @Bean
    public DiscoveryClientRouteLocator discoveryRouteLocator(ServerProperties server,
        ZuulProperties zuulProperties) {
      DiscoveryClient discovery = new NamespaceDiscoveryClient(this.env, this.discovery);
      return new DiscoveryClientRouteLocator(server.getServletPrefix(), discovery, zuulProperties,
          serviceRouteMapper()) {
      	@Override
        protected LinkedHashMap<String, ZuulRoute> locateRoutes() {
          LinkedHashMap<String, ZuulRoute> routes = super.locateRoutes();
          for (ZuulRoute route : routes.values()) {
            route.setStripPrefix(false);
          }
          return routes;
        }
      };
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
//    @Value("${a}")
//    private String from;

    @RequestMapping("/from")
    public String from() {

        return "zj";
    }
    
    
}
