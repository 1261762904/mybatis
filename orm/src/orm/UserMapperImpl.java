package orm;



public class UserMapperImpl implements UserMapper {

	@Override
	public void add(User u) {
		System.out.println("进入add(User u)");

	}

	@Override
	public User find(int id) {
		System.out.println("进入User find(int id)");
		return null;
	}


	@Override
	public User find(int id, String name) {
		System.out.println("进入User find(int id, String name)");
		return null;
	}

}
