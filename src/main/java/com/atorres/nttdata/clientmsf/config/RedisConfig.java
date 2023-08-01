package com.atorres.nttdata.clientmsf.config;

import com.atorres.nttdata.clientmsf.model.dao.ClientDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**.
 * Clase configuracion Redis
 */
@Configuration
public class RedisConfig {
  /**.
   * Bean para configurar Redis
   *
   * @param redisConnectionFactory interfac conexion reactiva para Redis
   *
   * @return ReactiveHashOperations
   */
  @Bean(name = "ReactiveHashOperationsCustom")
  public ReactiveHashOperations<String, String,
      ClientDao> hashOperations(ReactiveRedisConnectionFactory redisConnectionFactory) {
    var template = new ReactiveRedisTemplate<>(
        redisConnectionFactory,
        RedisSerializationContext.<String,
                ClientDao>newSerializationContext(new StringRedisSerializer())
            .hashKey(new GenericToStringSerializer<>(String.class))
            .hashValue(new Jackson2JsonRedisSerializer<>(ClientDao.class))
            .build()
    );
    return template.opsForHash();
  }
}
