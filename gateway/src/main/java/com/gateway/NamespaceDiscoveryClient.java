package com.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
/**
 * 
 * @author author
 *
 */
public class NamespaceDiscoveryClient implements DiscoveryClient {

  public static final String DESCRIPTION = "Spring Cloud Eureka Namespace Discovery Client";
  public static final String MS_INCLUDES = "fae.ms.includes";

  private final Environment env;
  private final DiscoveryClient client;

  public NamespaceDiscoveryClient(Environment env, DiscoveryClient client) {
    this.env = env;
    this.client = client;
  }

  @Override
  public String description() {
    return DESCRIPTION;
  }

  @SuppressWarnings("deprecation")
  @Override
  public ServiceInstance getLocalServiceInstance() {
    return client.getLocalServiceInstance();
  }

  @Override
  public List<ServiceInstance> getInstances(String serviceId) {
    return client.getInstances(serviceId);
  }

  @Override
  public List<String> getServices() {
    Set<String> services = StringUtils.commaDelimitedListToSet(env.getProperty(MS_INCLUDES, ""));
    services.retainAll(client.getServices());
    return new ArrayList<String>(services);
  }
}

