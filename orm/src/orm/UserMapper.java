package orm;



import orm.anno.Insert;
import orm.anno.Mapper;
import orm.anno.Select;


@Mapper
public interface UserMapper {

	@Insert("insert into users() values()")
	void add(User u);

	@Select("select id,name,email,phone from staff where id=#{id} and name=#{name}")
	User find(int id,String name);
	
	@Select("select id,name,email,phone,job from staff where id=#{id}")
	User find(int id);
	

}
