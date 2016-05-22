package com.dedmo.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.AddrUtil;

@SpringBootApplication
@RestController
public class SpringBootGradleBasedDemoApplication {

	@RequestMapping("/hellotest")
	public String method() {
		 MemcachedClient mc = null;
		try {
		    String vcap_services = System.getenv("VCAP_SERVICES");
		    if (vcap_services != null && vcap_services.length() > 0) {
		        // parsing rediscloud credentials
		    	JsonRootNode root = new JdomParser().parse(vcap_services);
		        JsonNode memcachedcloudNode = root.getNode("memcachedcloud");
		        JsonNode credentials = memcachedcloudNode.getNode(0).getNode("credentials");

		        // building the memcached client
		        AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
		                new PlainCallbackHandler(credentials.getStringValue("username"), credentials.getStringValue("password")));

		        mc = new MemcachedClient(
		                  new ConnectionFactoryBuilder()
		                      .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
		                      .setAuthDescriptor(ad).build(),
		                  AddrUtil.getAddresses(credentials.getStringValue("servers")));
		    }
		} catch (Exception ex) {
		    // vcap_services could not be parsed.
			ex.printStackTrace();
		}
		
		
		mc.set("foo", 0, "bar");
		Object value = mc.get("foo");
		return "HELLO TEST from Spring boot...value from memcached: Services"+value;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootGradleBasedDemoApplication.class, args);
		

	}
}
