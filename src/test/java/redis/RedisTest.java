package redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisTest {
	
	@SuppressWarnings("resource")
	@Test
	public void testRedis() {
		Jedis jedis = new Jedis("47.93.17.148", 6379, 100000);
		
		int i = 0;
		
		long start = System.currentTimeMillis();
		
		while(true) {
			long end  = System.currentTimeMillis();
			if(end - start > 1000) {
				break;
			}
			jedis.set("test" + i, i+"");
			i++;
		}
		
		System.out.println("每秒执行：" + i + " 次.");
	}
	
	
	
	@Test
	public void testTransaction(){
		Jedis jedis = new Jedis("localhost",6379,100000);
		Transaction transaction = jedis.multi();
		
		transaction.set("k1", "v1");
		transaction.set("k2", "v2");
		
		transaction.exec();
	}
	
	
	
	/*@Test
	public void testObj() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
		Student stu = new Student();
		stu.setName("wangsan");
		stu.setAge(20);
		redisTemplate.opsForValue().set("student1", stu);
		
		Student stu2 = (Student) redisTemplate.opsForValue().get("student1");
		System.out.println(stu2.toString());
	}*/
	
	
}
