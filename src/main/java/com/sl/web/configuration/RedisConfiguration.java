package com.sl.web.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.DefaultJedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Configuration
public class RedisConfiguration {
	@Value("${redis.ip}")
	private String ip;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.database}")
	private int database;
	@Value("${redis.password}")
	private String password;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.timeout}")
	private int timeout;
	
	@Bean
	public JedisPoolConfig poolConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(this.maxTotal);
		config.setMaxIdle(this.maxIdle);
		config.setMaxWaitMillis(5000);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		config.setTestOnCreate(false);
		config.setTestWhileIdle(true);
		
		return config;
	}
	
	@Bean
	public RedisStandaloneConfiguration redisStandaloneConfiguration(){
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(this.ip, this.port);
		config.setDatabase(database);
		config.setPassword(this.password);
		
		return config;
	}
	
	@Bean
	public JedisClientConfiguration jedisClientConfiguration(JedisPoolConfig pool){
		DefaultJedisClientConfigurationBuilder builder = (DefaultJedisClientConfigurationBuilder) JedisClientConfiguration.builder();
		builder.usePooling();
		builder.readTimeout(Duration.ofMillis(this.timeout));
		builder.connectTimeout(Duration.ofMillis(this.timeout));
		
		return builder.poolConfig(pool).build();
	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration standaloneConfig, JedisClientConfiguration clientConfig){
		return new JedisConnectionFactory(standaloneConfig, clientConfig);
	}
	
	@Bean
    public FastJsonRedisSerializer<?> fastJsonRedisSerializer() {
        return new FastJsonRedisSerializer<>(Object.class);
    }
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory, FastJsonRedisSerializer<?> fastJsonRedisSerializer) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
	@Bean 
	public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory redisConnectionFactory){
		return new StringRedisTemplate(redisConnectionFactory);
	}
	
	static class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
		private Class<T> cls;
		
		static {
	        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
	    }
		
		public FastJsonRedisSerializer(Class<T> cls){
			this.cls = cls;
		}
		
		@Override
		public byte[] serialize(T t) {
			return JSON.toJSONBytes(t, SerializerFeature.WriteClassName);
		}

		@Override
		public T deserialize(byte[] bytes) {
			if(bytes == null || bytes.length <= 0){
	            return null;
	        }
			return JSON.parseObject(bytes, this.cls);
		}
	}
}
