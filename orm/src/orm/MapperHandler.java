package orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapperHandler implements InvocationHandler {

	UserMapper mapper;

	public MapperHandler(UserMapper mapper) {
		super();
		this.mapper = mapper;
	}

	/**
	 * 
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// 反射
		// String name = method.getName();
		Class[] pars = method.getParameterTypes();
		Class r = method.getReturnType();
		Annotation[] annos = method.getAnnotations();
		// System.out.println(name);
		// System.out.println(Arrays.toString(pars));
		// System.out.println(r.getName());
		// System.out.println(Arrays.toString(annos));
		Object o = null;
		if (annos != null) {
			for (Annotation annotation : annos) {
				String type = annotation.annotationType().getSimpleName();
				switch (type) {
				case "Insert":
					System.out.printf("%s, %s, %s\n", method.getName(), "insert", annotation.toString());
					break;

				case "Select":
					String str = annotation.toString();
					System.out.println(str);
					String sql = str.substring(str.indexOf("\"") + 1, str.lastIndexOf("\""));
					if (str.indexOf("#") != -1) {
						String[] array = sql.split("#");
						sql = "";
						for (int i = 0; i < array.length; i++) {
							System.out.println(array[i]);
							StringBuilder sb = new StringBuilder(array[i]);
							if (array[i].indexOf("{") != -1) {
								sb.replace(array[i].indexOf("{"), array[i].indexOf("}") + 1, "?");
							}
							sql += sb;
						}
					}
					System.out.println("sql语句:" + sql);
					System.out.println(r.getName());
					o = select(sql, r, args);
						Method[] m = o.getClass().getMethods();
						Field[] fields = o.getClass().getDeclaredFields();
						for (Field field : fields) {
							String name = field.getName();
							for (int i = 0; i < m.length; i++) {
								if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
									System.out.print(m[i].getName() + " ");
									System.out.println(m[i].invoke(o));
								}
							}
						}
					break;
				}
			}
		}
		method.invoke(mapper, args);

		return o;
	}

	private Object select(String sql, Class r, Object[] args)
			throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Object o = r.newInstance();
		List<Object> list = new ArrayList<>();
		Connection conn = ConnectionDB.getConnection();
		PreparedStatement stat = conn.prepareStatement(sql);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof Integer) {
					stat.setInt(i + 1, (int) args[i]);
				} else if (args[i] instanceof String) {
					stat.setString(i + 1, (String) args[i]);
				} else if (args[i] instanceof Boolean) {
					stat.setBoolean(i + 1, (boolean) args[i]);
				}
			}
		}
		ResultSet rs = stat.executeQuery();
		while (rs.next()) {
			// 将所需列提取出来
			String temp = sql.substring(sql.indexOf("select") + 6, sql.indexOf("from"));
			String[] arrays = temp.substring(temp.indexOf(" ") + 1, temp.lastIndexOf(" ")).split(",");
			Field[] fields = r.getDeclaredFields();

			for (String array : arrays) {
				for (Field field : fields) {
					if (field.getName().equals(array)) {
						String name = field.getName();
						String methodStr = "set" + name.toUpperCase().substring(0, 1) + name.substring(1);
						Method method = r.getMethod(methodStr, new Class[] { field.getType() });
						switch (field.getType().getSimpleName()) {
						case "String":
							method.invoke(o, rs.getString(field.getName()));
							break;
						case "int":
							method.invoke(o, rs.getInt(field.getName()));
							break;
						case "boolean":
							method.invoke(o, rs.getBoolean(field.getName()));
							break;
						default:
							break;
						}
					}
				}
			}
		}
		return o;
	}

}
