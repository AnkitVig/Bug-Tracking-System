
public class AuthenticationManager {
	AuthenticationManager DB = new AuthenticationManager();
	
	public AuthenticationManager()
	{
		//setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/logo.png"));
		
	}
	public static boolean varifyLogin(String username,String password, String role)
	{
		
		boolean login = false; 
		if(username.equals("admin") && password.equals("1234"))
		{
			//System.out.println(login);
			login = true;
			//System.out.println(login);
		}
		//System.out.println(login);
		return login;
		
	}

}
