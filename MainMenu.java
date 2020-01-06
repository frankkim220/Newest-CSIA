import java.util.*;
import java.io.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private JTable mainTable;
	private JTextField textSearchField;
	public static DefaultTableModel mainModel;
	private File myFile;
	private File coffeeFile;
	public static String[] masterColumns = {"Client Name", "Coffee A", "Coffee B", "Coffee C"};
	
	private ArrayList<Client> masterList;
	private ArrayList<CoffeeBean> coffeeList;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenu() {
		setResizable(false);
		masterList = new ArrayList<Client>();
		coffeeList = new ArrayList<CoffeeBean>();
		myFile = new File("Coffee Order Database.txt");
		coffeeFile = new File("Coffee Beans.txt");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 450);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File...");
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (myFile.createNewFile()) 
					{
						initialWriteFile();
					}
					loadFile(myFile);
					loadCoffees(coffeeFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mnFile.add(mntmOpenFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeClientFile();
			}
		});
		mnFile.add(mntmSave);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeClientFile();
				writeCoffeeFile();
				System.exit(0);
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmEditCoffee = new JMenuItem("Edit Coffee");
		mntmEditCoffee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditCoffee editCoffee = new EditCoffee(coffeeList);
				editCoffee.setVisible(true);
			}
		});
		
		JMenu mnEditTable = new JMenu("Edit Table");
		mnEdit.add(mnEditTable);
		
		JMenuItem mntmAddClient = new JMenuItem("Add Client");
		mntmAddClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddClient addCli = new AddClient(mainTable, masterList);
				addCli.setVisible(true);
				resetTable();
			}
		});
		mnEditTable.add(mntmAddClient);
		
		JMenuItem mntmEditClient = new JMenuItem("Edit Client");
		mntmEditClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditClient editCli = new EditClient(mainTable, masterList);
				editCli.setVisible(true);
				resetTable();
			}
		});
		mnEditTable.add(mntmEditClient);
		
		JMenuItem mntmRemoveClient = new JMenuItem("Remove Client");
		mntmRemoveClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RemoveClient delCli = new RemoveClient(mainTable, masterList);
				delCli.setVisible(true);
				resetTable();
			}
		});
		mnEditTable.add(mntmRemoveClient);
		mnEdit.add(mntmEditCoffee);
		
		JMenu mnStatistics = new JMenu("Statistics");
		mnStatistics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Statistics stats = new Statistics(masterList, coffeeList);
				stats.setVisible(true);
			}
		});
		mnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Statistics stats = new Statistics(masterList, coffeeList);
				stats.setVisible(true);
			}
		});
		menuBar.add(mnStatistics);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[100px:200px,grow]", "[][][][][][][][][]"));
		
		JLabel lblCoffeeDatabase = new JLabel("Coffee Orders Database");
		lblCoffeeDatabase.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblCoffeeDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCoffeeDatabase, "cell 0 2,growx");
		
		textSearchField = new JTextField();
		contentPane.add(textSearchField, "flowx,cell 0 3,growx");
		textSearchField.setColumns(10);
        
        mainTable = new JTable();
        mainTable.setEnabled(false);
        mainTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane mainTableScroll = new JScrollPane(mainTable);
        contentPane.add(mainTableScroll, "cell 0 7,grow");
        mainTable.setPreferredScrollableViewportSize(new Dimension(200, 750));
        mainTable.setFillsViewportHeight(true);
        mainTable.setModel(new DefaultTableModel(getListToArray(masterList), masterColumns));
        mainTable.setAutoCreateRowSorter(true);
        setJTableColumnsWidth(mainTable, 750, 55, 15, 15, 15);
        setJustify(mainTable);
        
        JButton btnRemoveClient = new JButton("Remove Client");
        btnRemoveClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RemoveClient delCli = new RemoveClient(mainTable, masterList);
        		delCli.setVisible(true);
        		resetTable();
        	}
        });
        
        JButton btnAddClient = new JButton("Add Client");
        contentPane.add(btnAddClient, "flowx,cell 0 4,growx");
        btnAddClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		AddClient addCli = new AddClient(mainTable, masterList);
        		addCli.setVisible(true);
        		resetTable();
        	}
        });
        
        JButton btnEditClient = new JButton("Edit Client");
        btnEditClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		EditClient editCli = new EditClient(mainTable, masterList);
        		editCli.setVisible(true);
        		resetTable();
        	}
        });
        contentPane.add(btnEditClient, "cell 0 4,growx");
        contentPane.add(btnRemoveClient, "cell 0 4,growx");

        
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		searchSet();
        	}
        });
        contentPane.add(btnSearch, "cell 0 3");
        
	}
	
	/* loads indicated file, separating values by commas
	 * loads data of each client and their order
	 * it writes each to their respective arraylist, then calls fillData to fill the table with the orders
	*/
	public void loadFile(File file)
	{
		try {
			Scanner scan = new Scanner(file);
			
			ArrayList<String[]> rows = new ArrayList <String[]> ();
			while (scan.hasNextLine())
			{
				String str = scan.nextLine();
				String[] arr = str.split(",");
				rows.add(arr);
			}
			for (int i = 0; i < rows.size(); i++)
			{
				String[] array = rows.get(i);
				Client cli = new Client(array[0], Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3]));
				masterList.add(cli);
			}
			fillData(mainTable, masterList);
			resetTable();
			
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/* loads indicated file, separating values by commas
	 * loads the data of each coffee bean
	 * it writes each to their respective arraylist
	*/
	public void loadCoffees(File file)
	{
		try {
			Scanner scan = new Scanner(file);
			
			ArrayList<String[]> coffees = new ArrayList<String[]>();
			int count = 0;
			while (scan.hasNextLine() && count < 3)
			{
				String str = scan.nextLine();
				String[] arr = str.split(",");
				coffees.add(arr);
				count++;
			}
			for (int i = 0; i < coffees.size(); i++)
			{
				String[] array = coffees.get(i);
				CoffeeBean coffeeBean = new CoffeeBean(i, Double.parseDouble(array[0]), Double.parseDouble(array[1]), Double.parseDouble(array[2]));
				coffeeList.add(coffeeBean);
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//writes to respective file using printwriter and separating values with commas
	public void writeClientFile()
	{
		try
		{
			PrintWriter pw = new PrintWriter(new FileWriter(myFile));
			String str = "";
			for (Client cli: masterList)
			{
				str = cli.getName() + "," + cli.getA() + "," + cli.getB() + "," + cli.getC();
				pw.println(str);
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//writes to respective file using printwriter and separating values with commas
	public void writeCoffeeFile()
	{
		try
		{
			PrintWriter pw = new PrintWriter(new FileWriter(coffeeFile));
			String str = "";
			for (CoffeeBean bean: coffeeList)
			{
				str = bean.getPrice() + "," + bean.getCost() + "," + bean.getWeight();
				pw.println(str);
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//will create an initial file if the file does not exist
	public void initialWriteFile()
	{
		try {
			PrintWriter pwClient = new PrintWriter(new FileWriter(myFile));
			PrintWriter pwCoffee = new PrintWriter(new FileWriter(coffeeFile));
			String[] clientArr = {"Peet's (Irvine),11,10,9", 
								  "Peet's (Tustin),13,7,9", 
								  "With Cream (Irvine),2,2,3", 
								  "Talia's (Norwalk),14,15,17", 
								  "Talia's (Irvine),12,14,10", 
								  "With Cream (Norwalk),2,10,0", 
								  "The Coffee Shop (La Mirada),2,0,4"};
			String[] coffeeArr = {"13.0,5.0,1.0", 
								  "10.25,6.50,2.0", 
								  "11.50,4.5,1.5"};
			for (String str: clientArr)
			{
				pwClient.println(str);
			}
			for (String str: coffeeArr)
			{
				pwCoffee.println(str);
			}
			pwClient.close();
			pwCoffee.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//converts the arraylist of clients to a matrix of objects and returns it
	public static Object[][] getListToArray(ArrayList<Client> list)
	{
		Object[][] data = new Object[list.size()][masterColumns.length];
		for (int i = 0; i < list.size(); i++)
		{
			Client cli =  list.get(i);
			data[i][0] = cli.getName();
			data[i][1] = cli.getA();
			data[i][2] = cli.getB();
			data[i][3] = cli.getC();
		}
		return data;
	}
	
	//resets mainTable to original table model
	public void resetTable()
	{
		mainModel = new DefaultTableModel(getListToArray(masterList), masterColumns) {
			Class[] types = { String.class, Integer.class, Integer.class, Integer.class };
			public Class getColumnClass(int columnIndex) 
			{
				return this.types[columnIndex];
			}
		};
		
		mainTable.setModel(mainModel);
		setJTableColumnsWidth(mainTable, 750, 55, 15, 15, 15);
		setJustify(mainTable);
	}
	
	//fills mainTable to appropriate parameters
	public static void fillData(JTable table, ArrayList<Client> list)
	{
		DefaultTableModel newTableModel = new DefaultTableModel(getListToArray(list), masterColumns);
        table.setModel(new DefaultTableModel(getListToArray(list), masterColumns) {
        	Class[] types = {String.class, Integer.class, Integer.class, Integer.class};

            @Override
            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }
        });
        setJTableColumnsWidth(table, 750, 55, 15, 15, 15);
        setJustify(table);
	}
	
	//searches for client and displays appropriate mainTable
	public void searchSet()
	{
		ArrayList<Client> searchSet = new ArrayList<Client>();
		String str = textSearchField.getText();
		if (str.contentEquals(""))
		{
			resetTable();
			return;
		}
		ArrayList<Integer> indexList = dataClientSearch(str);
		if (indexList.isEmpty())
		{
			mainTable.setModel(new DefaultTableModel());
		}
		for (int i = 0; i < indexList.size(); i++)
		{
			searchSet.add(masterList.get(indexList.get(i)));
		}
		mainTable.setModel(new DefaultTableModel(getListToArray(searchSet), masterColumns));
	}
	
	//searches for client and returns index if found, -1 if not
	public ArrayList<Integer> dataClientSearch (String str)
	{
		ArrayList<Integer> list = new ArrayList<Integer> ();
		for (int i = 0; i < masterList.size(); i++)
		{
			if (masterList.get(i).getName().toLowerCase().contains(str.toLowerCase()))
			{
				list.add(i);
			}
		}
		return list;
	}
	
	//sets width of each column of the table
	public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) 
	    {
	        total += percentages[i];
	    }
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) 
	    {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)(tablePreferredWidth * (percentages[i] / total)));
	    }
	}
	
	//justifies the table columns storing numerical values
	public static void setJustify(JTable table)
	{
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < masterColumns.length; i++)
        {
        	table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
}
