package orm;



import orm.anno.Insert;
import orm.anno.Mapper;
import orm.anno.Select;

/**
 * 定义数据操作的方法，注解映射了实质要执行的 SQL
 * 
 * @author wtao
 *
 */
@Mapper
public interface UserMapper {

	@Insert("insert into users() values()")
	void add(User u);

	@Select("select id,name,email,phone from staff where id=#{id} and name=#{name}")
	User find(int id,String name);
	
	@Select("select id,name,email,phone,job from staff where id=#{id}")
	User find(int id);
	

}
