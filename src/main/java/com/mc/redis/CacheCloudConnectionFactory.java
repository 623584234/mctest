/*package com.mc.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.sohu.tv.builder.ClientBuilder;
import com.sohu.tv.builder.RedisClusterBuilder;
import com.sohu.tv.builder.RedisSentinelBuilder;
import com.sohu.tv.builder.RedisStandaloneBuilder;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Pool;

public class CacheCloudConnectionFactory extends JedisConnectionFactory {

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public CacheCloudConnectionFactory() {
	}

	public CacheCloudConnectionFactory(JedisShardInfo shardInfo) {
		super(shardInfo);
	}

	public CacheCloudConnectionFactory(JedisPoolConfig poolConfig) {
		this((RedisSentinelConfiguration) null, poolConfig);
	}

	public CacheCloudConnectionFactory(RedisSentinelConfiguration sentinelConfig) {
		this(sentinelConfig, null);
	}

	public CacheCloudConnectionFactory(RedisSentinelConfiguration sentinelConfig, JedisPoolConfig poolConfig) {
		super(sentinelConfig, poolConfig);
	}

	public CacheCloudConnectionFactory(RedisClusterConfiguration clusterConfig) {
		super(clusterConfig);
	}

	public CacheCloudConnectionFactory(RedisClusterConfiguration clusterConfig, JedisPoolConfig poolConfig) {
		super(clusterConfig, poolConfig);
	}

	protected JedisCluster createCluster(RedisClusterConfiguration clusterConfig, GenericObjectPoolConfig poolConfig) {
		RedisClusterBuilder clientBuilder = ClientBuilder.redisCluster((new Long(appId)).longValue());
		clientBuilder = clientBuilder.setJedisPoolConfig(getPoolConfig());
		return clientBuilder.build();
	}

	protected Pool createRedisSentinelPool(RedisSentinelConfiguration config) {
		RedisSentinelBuilder clientBuilder = ClientBuilder.redisSentinel((new Long(appId)).longValue());
		clientBuilder.setPoolConfig(getPoolConfig());
		return clientBuilder.build();
	}

	protected Pool createRedisPool() {
		RedisStandaloneBuilder clientBuilder = ClientBuilder.redisStandalone((new Long(appId)).longValue());
		clientBuilder.setPoolConfig(getPoolConfig());
		return clientBuilder.build();
	}

	private String appId;
}
*/