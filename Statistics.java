import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class Statistics extends JFrame {

	private JPanel contentPane;
	private JTable statsTable;
	public static String[] statsColumns = {"", "Quantity (bags)", "Revenues ($)", "Costs ($)", "Profits ($)", "Weight (lbs)"};
	private ArrayList<Client> masterList;
	private ArrayList<CoffeeBean> coffeeList;

	public Statistics(ArrayList<Client> cliList, ArrayList<CoffeeBean> coffList) {
		masterList = cliList;
		coffeeList = coffList;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][]", "[]"));
		
		JLabel lblStatistics = new JLabel("Statistics");
		contentPane.add(lblStatistics, "cell 1 0,alignx center");
		
		statsTable = new JTable();
		statsTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JScrollPane mainTableScroll = new JScrollPane(statsTable);
		contentPane.add(mainTableScroll, "cell 1 1,grow");
		statsTable.setPreferredScrollableViewportSize(new Dimension(550, 300));
		statsTable.setFillsViewportHeight(true);
		statsTable.setAutoCreateRowSorter(true);
		fillTable();
	}
	
	//fills table with the proper statistical information, including a total at the bottom
	public void fillTable()
	{
		String[][] data = new String[coffeeList.size()+1][statsColumns.length];
		int totalQ = 0;
		double totalRev = 0;
		double totalCost = 0;
		double totalProf = 0;
		double totalWt = 0;
		for (int i = 0; i < coffeeList.size(); i++)
		{
			CoffeeBean coffee = coffeeList.get(i);
			data[i][0] = coffee.toString();
			data[i][1] = String.valueOf(getQuantity(i));
			totalQ += getQuantity(i);
			data[i][2] = Double.toString(round(coffee.getPrice() * Double.parseDouble(data[i][1]), 2));
			totalRev += coffee.getPrice() * Double.parseDouble(data[i][1]);
			data[i][3] = Double.toString(round(coffee.getCost() * Double.parseDouble(data[i][1]), 2));
			totalCost += coffee.getCost() * Double.parseDouble(data[i][1]);
			data[i][4] = String.valueOf((coffee.getPrice() - coffee.getCost()) * Double.parseDouble(data[i][1]));
			totalProf += (coffee.getPrice() - coffee.getCost()) * Double.parseDouble(data[i][1]);
			data[i][5] = String.valueOf(coffee.getWeight() * Double.parseDouble(data[i][1]));
			totalWt += coffee.getWeight() * Double.parseDouble(data[i][1]);
		}
		data[coffeeList.size()][0] = "Total";
		data[coffeeList.size()][1] = String.valueOf(totalQ);
		data[coffeeList.size()][2] = Double.toString(round(totalRev, 2));
		data[coffeeList.size()][3] = Double.toString(round(totalCost, 2));
		data[coffeeList.size()][4] = Double.toString(round(totalProf, 2));
		data[coffeeList.size()][5] = Double.toString(round(totalWt, 2));
		
		DefaultTableModel statsModel = new DefaultTableModel(data, statsColumns) {
			Class[] types = { String.class, Integer.class, Double.class, Double.class, Double.class, Double.class };
			public Class getColumnClass(int columnIndex) 
			{
				return this.types[columnIndex];
			}
		};
		statsTable.setModel(statsModel);
		setJTableColumnsWidth(statsTable, 550, 10, 18, 18, 18, 18, 18);
		setJustify(statsTable);
	}
	
	//justifies table
	public static void setJustify(JTable table)
	{
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < statsColumns.length; i++)
        {
        	table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	//returns quantities of the selected CoffeeBean for all clients
	public int getQuantity(int id)
	{
		int count = 0;
		if (id == 0)
		{
			for (Client cli: masterList)
			{
				count += cli.getA();
			}
		}
		else if (id == 1)
		{
			for (Client cli: masterList)
			{
				count += cli.getB();
			}
		}
		else
		{
			for (Client cli: masterList)
			{
				count += cli.getC();
			}
		}
		return count;
	}
	
	//sets column width of the table accordingly
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
	
	//rounds the value parameter to the number of places indicted by the places parameter
	static double round(double value, int places) 
	{
	    if (places < 0) 
	    {
	    	throw new IllegalArgumentException();
	    }
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
