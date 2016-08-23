Find Running process : netstat -o -n -a| findstr 0.0:8080
	eg. TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       4468
Kill process: taskkill /F /PID 4468


The initial registration is actually trigger by the first heartbeat: the client tries to send the first heartbeat and receives a "not found" answer from the server which means it doesn't know the instance. The client then immediately attempts to register the instance. This process only happens 30s (eureka.instance.leaseRenewalIntervalInSeconds) after startup - hence the extra delay before the instance shows up in the registry.

You can always speed-up the initial registration process by lowering the value of eureka.client.initialInstanceInfoReplicationIntervalSeconds. This parameter controls the initial delay before the client transmits changes made to the InstanceInfo (like the UP/DOWN status). Because of how it is initialized, the InstanceInfo is always dirty after startup - so the client will always try to replicate it at least once. Since this replication is implemented by re-registering the instance against the server, using a very low value for the initial delay will trigger the instance registration quicker... without changing the heart beat interval and therefore keeping the self-protection mode safe.

During demo/development, if you want to detect "dead" instances quicker, I would suggest to play with the eureka.instance.leaseExpirationDurationInSeconds parameter instead. The value is set to 90s by default, which means a lease is expired after 3 consecutive missing heartbeats. This is of course pretty long during demo/dev, but you can always lower it to 30s - and this won't affect the self-protection feature.

1) Client Registration

When using the default configuration, registration happens at the first heartbeat sent to the server. Since the client just started, the server doesn't know anything about it and replies with a 404 forcing the client to register. The client then immediately issues a second call with all the registration information. The client is now registered.

The first heartbeat happens 30 seconds after startup (eureka.instance.leaseRenewalIntervalInSeconds) - so your instance won't appear in the Eureka registry before this interval.

(2) Server ResponseCache

The server maintains a response cache that is updated every 30s by default (eureka.server.response-cache-update-interval-ms). So even if your instance is just registered, it won't appear in the result of a call to the /eureka/apps REST endpoint.

However, your instance may appear in the UI web interface just after registration. This is because the web front-end bypasses the response cache used by the REST API...

If you know the instanceId, you can still get some details from Eureka about it by calling /eureka/apps/<appName>/<instanceId>. This endpoint doesn't make use of the response cache. But since it requires to know the instance, it is of no help in the discovery process...

So, it may take up to another 30s for other clients to discover your newly registered instance.

(3) Client cache refresh

Eureka client maintain a cache of the registry information. This cache is refreshed every 30 seconds by default ('eureka.client.registryFetchIntervalSeconds`). So again, it may take another 30s before a client decides to refresh its local cache and discover newly registered instances.

(4) LoadBalancer refresh

The load balancer used by Ribbon gets its information from the local Eureka client. It also maintains a local cache to avoid calling the discovery client for every request. This cache is refreshed every 30s (ribbon.serverListRefreshInterval). So again, it may take another 30s before your Ribbon client can make use of the newly registered instance.

Note: this local cache is apparently required only to reduce the cost of obtaining server information from the used ServerList. This is not the case with none of the server list provided by default: DiscoveryEnabledNIWSServerList with Eureka, ConfigurationBasedServerList without.