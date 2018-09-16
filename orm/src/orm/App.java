package orm;

import java.lang.reflect.Proxy;

// mybatis
// orm
// sql --> object 映射
public class App {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		UserMapper realUserMapper = new UserMapperImpl();
		MapperHandler handler = new MapperHandler(realUserMapper);
		UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
				realUserMapper.getClass().getInterfaces(), handler);

//		List<User> userList = userMapper.findAll();
User user = userMapper.find(20);
System.out.println(user.getName());
//		userMapper.find(20,"易昕");
//		userMapper.findAll();
		




	}
}
