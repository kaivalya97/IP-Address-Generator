import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.*;

/*Dynamic IP Address Generator by Group 26: Aaditya Shah, Kaivalya Shah, Mohit Vachhani*/

class GUI extends JFrame	//GUI class
{
	static String loggedinuser = new String();	//Contains name of the current logged in user
	static String loggedindept = new String();	//Contains department of the current logged in user
	static String loggedinip;	//IP address of the current logged in user
	static Linkedlist <Boolean> ITip = new Linkedlist <Boolean>();	//Linked list of boolean type which has 
	static Linkedlist <Boolean> Salesip = new Linkedlist <Boolean>();
	static Linkedlist <Boolean> HRip = new Linkedlist <Boolean>();
	static AvlTree ITTree = new AvlTree();
	static AvlTree SalesTree = new AvlTree();
	static AvlTree HRTree = new AvlTree();
	Linkedlist <JButton> buttons;	//Linked List of JButton type
	Linkedlist <JLabel> labels;	//Linked List of JLabel type
	Linkedlist <TextField> texts;	//Linked List of TextField type
	JPasswordField P1;	//Password field
	JComboBox<String> dept;	//Combo Box for choosing department
	static int findip = -1;
	static final long serialVersionUID = 42L;
	
	public GUI(String title)	//Constructor with title of window as argument
	{
		buttons = new Linkedlist<JButton>();	//Instantiating buttons linked list
		labels = new Linkedlist<JLabel>();	//Instantiating labels linked list
		texts = new Linkedlist<TextField>();	//Instantiating texts linked list
		switch(title)
		{
			case "Welcome":	//Welcome screen
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new GridLayout(4,1));	//Window layout with 4 rows and 1 column
				setSize(280,160);	//Appropriate size
				setLocation(350,300);
				labels.add(new JLabel("           Welcome to AKM IP Address Portal"));	//Instantiates new JLabel and adds to labels linked list
				add(labels.getFirst());	//Adds first node in labels linked list to frame
				buttons.add(new JButton("Login"));	//Login button
				buttons.getFirst().addActionListener(new ButtonListener());
				buttons.getFirst().setActionCommand("login");
				add(buttons.getFirst());	//Adds login button to frame
				buttons.add(new JButton("Sign up"));	//Sign Up button
				buttons.getLast().addActionListener(new ButtonListener());
				buttons.getLast().setActionCommand("signup");
				add(buttons.getLast());	//adds Sign Up button to frame
				buttons.add(new JButton("Admin Login"));
				buttons.getLast().addActionListener(new ButtonListener());
				buttons.getLast().setActionCommand("admin");
				add(buttons.getLast());	//adds Admin Login button
				setVisible(true);	//Displays the frame on screen
				break;
				
			case "Login":	//User login frame
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new GridLayout(3,2));
				setSize(275,150);
				setLocation(350,300);
				labels.add(new JLabel("Username"));
				add(labels.getFirst());
				texts.add(new TextField());
				add(texts.getFirst());
				labels.add(new JLabel("Password"));
				add(labels.getLast());
				P1 = new JPasswordField();
				add(P1);
				buttons.add(new JButton("Submit"));
				buttons.getFirst().addActionListener(new ButtonListener());
				buttons.getFirst().setActionCommand("submit");
				add(buttons.getFirst());
				buttons.add(new JButton("Return to Welcome screen"));
				buttons.getLast().addActionListener(new ButtonListener());
				buttons.getLast().setActionCommand("return");
				add(buttons.getLast());
				getRootPane().setDefaultButton(buttons.getFirst());
				setVisible(true);
				break;
				
			case "Sign Up":
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new GridLayout(3,2));
				setSize(275,150);
				setLocation(350,300);
				labels.add(new JLabel("Username"));
				add(labels.getFirst());
				texts.add(new TextField());
				add(texts.getFirst());
				labels.add(new JLabel("Password"));
				add(labels.getLast());
				P1 = new JPasswordField();
				add(P1);
				buttons.add(new JButton("Submit"));
				buttons.getFirst().addActionListener(new ButtonListener());
				buttons.getFirst().setActionCommand("newacc");
				add(buttons.getFirst());
				getRootPane().setDefaultButton(buttons.getFirst());
				String[] depts = {"IT","Sales","HR"};
				dept = new JComboBox<String>(depts);
				add(dept);
				setVisible(true);
				break;
				
			case "Portal":
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new GridLayout(3,2));
				setSize(275,150);
				setLocation(350,300);
				loggedinip = assignip();
				switch(loggedindept)
				{
					case "IT":
						ITTree.writetofile("IT");
						break;
					case "Sales":
						SalesTree.writetofile("Sales");
						break;
					case "HR":
						HRTree.writetofile("HR");
						break;
				}
				labels.add(new JLabel("      Hello "+loggedinuser+", your IP Address is "+loggedinip));
				add(labels.getFirst());
				buttons.add(new JButton("Log out"));
				buttons.getFirst().addActionListener(new ButtonListener());
				buttons.getFirst().setActionCommand("Logout");
				add(buttons.getFirst());
				getRootPane().setDefaultButton(buttons.getFirst());
				setVisible(true);
				break;
				
			case "Admin Login":
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new GridLayout(3,2));
				setSize(275,150);
				setLocation(350,300);
				labels.add(new JLabel("Username"));
				add(labels.getFirst());
				texts.add(new TextField());
				add(texts.getFirst());
				labels.add(new JLabel("Password"));
				add(labels.getLast());
				P1 = new JPasswordField();
				add(P1);
				buttons.add(new JButton("Submit"));
				buttons.getFirst().addActionListener(new ButtonListener());
				buttons.getFirst().setActionCommand("adminsubmit");
				add(buttons.getFirst());
				buttons.add(new JButton("Return to Welcome screen"));
				buttons.getLast().addActionListener(new ButtonListener());
				buttons.getLast().setActionCommand("return");
				add(buttons.getLast());
				getRootPane().setDefaultButton(buttons.getFirst());
				setVisible(true);
				break;
				
			case "Admin Portal":
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new FlowLayout());
				setSize(275,150);
				setLocation(350,300);
				labels.add(new JLabel("Username       Login time"));
				add(labels.getFirst());
				try
				{
					FileInputStream fr = new FileInputStream("login.txt");
					Scanner br = new Scanner(fr);
					int j = 0;
					String[] temp;
					while(br.hasNextLine())
					{
						temp = br.next().split(",");
						labels.add(new JLabel(temp[0]+"       "+temp[1]));
						add(labels.getLast());
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				labels.add(new JLabel("Username       Logout time"));
				add(labels.getLast());
				try
				{
					FileInputStream fr = new FileInputStream("logout.txt");
					Scanner br = new Scanner(fr);
					int j = 0;
					String[] temp;
					while(br.hasNextLine())
					{
						temp = br.next().split(",");
						labels.add(new JLabel(temp[0]+"       "+temp[1]));
						add(labels.getLast());
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				buttons.add(new JButton("Search by Name"));
				buttons.getLast().addActionListener(new ButtonListener());
				buttons.getLast().setActionCommand("SearchName");
				add(buttons.getLast());
				setSize(175,labels.getSize()*40);	//Variable height because the number of labels are dynamic
				setVisible(true);
				break;
			case "Search by Name":
				setTitle(title);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setLayout(new GridLayout(2,2));
				setSize(275,150);
				setLocation(350,300);
				labels.add(new JLabel("Name"));
				add(labels.getFirst());
				texts.add(new TextField());
				add(texts.getFirst());
				buttons.add(new JButton("Search"));
				buttons.getLast().addActionListener(new ButtonListener());
				buttons.getLast().setActionCommand("Search by Name");
				add(buttons.getLast());
				setVisible(true);
				break;
		}
	}

	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String id = event.getActionCommand();
			switch(id)
			{
				case "login":
					dispose();
					GUI Login = new GUI("Login");
					break;
					
				case "signup":
					dispose();
					GUI Signup = new GUI("Sign Up");
					break;
					
				case "submit":
					String pass = new String(P1.getPassword());
					int check = checkPassword(texts.getFirst().getText(),pass);
					if(check==1)
					{
						write(texts.getFirst().getText());
						loggedinuser = texts.getFirst().getText();
						GUI Portal = new GUI("Portal");
						dispose();
					}
					else if(check==0)
					{
						labels.add(new JLabel("Username and Password don't match"));
						add(labels.getLast());
						setLayout(new FlowLayout());
						setSize(450,150);
					}
					else
					{
						labels.add(new JLabel("The username doesn't exist"));
						add(labels.getLast());
						setLayout(new FlowLayout());
						setSize(450,150);
					}
					break;
					
				case "newacc":
					dispose();
					pass = new String(P1.getPassword());
					String dep = (String)dept.getSelectedItem();
					newacc(texts.getFirst().getText(),pass,dep);
					break;
					
				case "Logout":
					dispose();
					writeout(loggedinuser);
					break;
					
				case "admin":
					dispose();
					GUI adminlogin = new GUI("Admin Login");
					break;
					
				case "adminsubmit":
					pass = new String(P1.getPassword());
					if(!texts.getFirst().getText().equals("admin"))
					{
						labels.add(new JLabel("Incorrect admin username"));
						add(labels.getLast());
					}
					else if(!pass.equals("admin"))
					{
						labels.add(new JLabel("Incorrect admin password"));
						add(labels.getLast());
					}
					else
					{
						dispose();
						GUI adminportal = new GUI ("Admin Portal");
					}
					break;
					
				case "return":
					dispose();
					GUI Welcome = new GUI("Welcome");
					break;
					
				case "SearchName":
					GUI SearchName = new GUI("Search by Name");
					break;
					
				case "Search by Name":
					String temp = texts.getFirst().getText();
					boolean it,sales,hr;
					readTree("IT");
					readTree("Sales");
					readTree("HR");
					it = ITTree.search(temp);
					sales = SalesTree.search(temp);
					hr = HRTree.search(temp);
					if(it==true)
					{
						labels.add(new JLabel(temp+" is logged into IT department"));
						add(labels.getLast());
						setSize(300,150);
						setVisible(true);
					}
					else if(sales==true)
					{
						labels.add(new JLabel(temp+" is logged into Sales department"));
						add(labels.getLast());
						setSize(300,150);
						setVisible(true);
					}
					else if(hr==true)
					{
						labels.add(new JLabel(temp+" is logged into HR department"));
						add(labels.getLast());
						setSize(300,150);
						setVisible(true);
					}
					else
					{
						labels.add(new JLabel("The user is not currently logged in"));
						add(labels.getLast());
						setSize(300,150);
						setVisible(true);
					}
					break;
			}
		}
	}
	
	public static int checkPassword(String username, String password)
	{
		try
		{
			FileInputStream fr = new FileInputStream("acc.txt");
			Scanner br = new Scanner(fr);
			int j = 0;
			boolean flag = false;
			Linkedlist<String> usernames = new Linkedlist<String>();
			Linkedlist<String> passwords = new Linkedlist<String>();
			Linkedlist<String> depts = new Linkedlist<String>();
			String[] temp;
			while(br.hasNextLine())
			{
				temp = br.next().split(",");
				usernames.add(temp[0]);
				passwords.add(temp[1]);
				depts.add(temp[2]);
			}
			for(int i=0;i<usernames.getSize();i++)
			{
				if(username.equals(usernames.get(i)))
				{
					flag = true;
					j=i;
				}
			}
			if(flag==false)	return -1;
			if(password.equals(passwords.get(j)))
			{
				loggedindept = depts.get(j);
				readTree(loggedindept);
				return 1;
			}
			else
				return 0;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void readTree(String dept)
	{
		try
		{
			File file = new File(dept+"Tree");
			if(file.exists()==true)
			{
				FileInputStream fr = new FileInputStream(dept+"Tree");
				Scanner br = new Scanner(fr);
				String[] temp;
				String[] tempi;
				temp = br.next().split(";");
				for(int i=0;i<temp.length;i++)
				{
					tempi = temp[i].split(",");
					switch(loggedindept)
					{
						case "IT":
							ITTree.insert(Integer.parseInt(tempi[0]),tempi[1]);
							break;
						case "Sales":
							SalesTree.insert(Integer.parseInt(tempi[0]),tempi[1]);
							break;
						case "HR":
							HRTree.insert(Integer.parseInt(tempi[0]),tempi[1]);
							break;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String time()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());
	}
	
	public void write(String a)
	{
		String filename="login.txt";
		try
		{
			FileWriter fw = new FileWriter(filename,true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append("\n"+a+",");
			bw.append(time());
			bw.close();
		}
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	}
	
	public void writeout(String a)
	{
		String filename="logout.txt";
		try
		{
			FileWriter fw = new FileWriter(filename,true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append("\n"+a+",");
			bw.append(time());
			bw.close();
		}
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
		filename = new String(loggedindept+"ip");
		try
		{
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			String str = new String (""+loggedinip);
			bw.write(""+str.charAt(str.length()-1));
			bw.close();
		}
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	}

	static public void deleteFile(File path)
	{
		try
		{
			FileWriter fw = new FileWriter(path,false);
			BufferedWriter bw = new BufferedWriter(fw);
			fw.write("");
			path.delete();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void newacc(String username, String password, String dep)
	{
		String filename="acc.txt";
		try
		{
			FileWriter fw=new FileWriter(filename,true);
			BufferedWriter br=new BufferedWriter(fw);
			br.append("\n"+username+","+password+","+dep);
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String assignip()
	{
		String out = new String();
		switch(loggedindept)
		{
			case "IT":
				File file = new File("ITip");
				if(file.exists()==true)
				{
					try
					{
						FileInputStream fr = new FileInputStream(file);
						Scanner br = new Scanner(fr);
						String in =  br.next();
						int ip = Integer.parseInt(in);
						deleteFile(file);
						out = ("192.168.1."+ip);
						ITTree.insert(ip, loggedinuser);
					}
					catch(Exception e)
					{
						int temp = ITTree.nodes;
						temp++;
						ITTree.insert(temp,loggedinuser);
						out = ("192.168.1."+temp);
					}
				}
				else
				{
					int temp = ITTree.nodes;
					temp++;
					ITTree.insert(temp,loggedinuser);
					out = ("192.168.1."+temp);
				}
				break;
				
			case "Sales":
				file = new File("Salesip");
				if(file.exists()==true)
				{
					try
					{
						FileInputStream fr = new FileInputStream(file);
						Scanner br = new Scanner(fr);
						String in =  br.next();
						int ip = Integer.parseInt(in);
						deleteFile(file);
						out = ("192.168.2."+ip);
						SalesTree.insert(ip, loggedinuser);
					}
					catch(Exception e)
					{
						int temp = SalesTree.nodes;
						temp++;
						SalesTree.insert(temp,loggedinuser);
						out = ("192.168.2."+temp);
					}
				}
				else
				{
					int temp = SalesTree.nodes;
					temp++;
					SalesTree.insert(temp,loggedinuser);
					out = ("192.168.2."+temp);
				}
				break;
						
			case "HR":
				file = new File("HRip");
				if(file.exists()==true)
				{
					try
					{
						FileInputStream fr = new FileInputStream(file);
						Scanner br = new Scanner(fr);
						String in =  br.next();
						int ip = Integer.parseInt(in);
						deleteFile(file);
						out = ("192.168.3."+ip);
						HRTree.insert(ip, loggedinuser);
					}
					catch(Exception e)
					{
						int temp = HRTree.nodes;
						temp++;
						HRTree.insert(temp,loggedinuser);
						out = ("192.168.3."+temp);
					}
				}
				else
				{
					int temp = HRTree.nodes;
					temp++;
					HRTree.insert(temp,loggedinuser);
					out = ("192.168.3."+temp);
				}
				break;
		}
		return out;
	}
}