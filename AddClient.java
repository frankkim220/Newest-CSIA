import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddClient extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField textNewCoffee;
	private JTextField textCfeA;
	private JTextField textCfeB;
	private JTextField textCfeC;

	private JTable mainTable;
	private ArrayList<Client> mainList;

	/**
	 * Create the dialog.
	 */
	public AddClient(JTable table, ArrayList<Client> list) {
		mainTable = table;
		mainList = list;
		
		setBounds(100, 100, 425, 215);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][]"));
		
		JLabel lblAddClient = new JLabel("Add Client");
		contentPanel.add(lblAddClient, "cell 0 0 2 1,alignx center");
		JLabel lblNameOfNew = new JLabel("New Client");
		contentPanel.add(lblNameOfNew, "cell 0 1,alignx trailing");
		
		textNewCoffee = new JTextField();
		contentPanel.add(textNewCoffee, "cell 1 1,growx");
		textNewCoffee.setColumns(10);
		
		JLabel lblCoffeeA = new JLabel("Coffee A quantity");
		contentPanel.add(lblCoffeeA, "cell 0 2,alignx trailing");
		
		textCfeA = new JTextField();
		contentPanel.add(textCfeA, "cell 1 2,growx");
		textCfeA.setColumns(10);
		
		JLabel lblCoffeeB = new JLabel("Coffee B quantity");
		contentPanel.add(lblCoffeeB, "cell 0 3,alignx trailing");
		
		textCfeB = new JTextField();
		contentPanel.add(textCfeB, "cell 1 3,growx");
		textCfeB.setColumns(10);
		
		JLabel lblCoffeeC = new JLabel("Coffee C quantity");
		contentPanel.add(lblCoffeeC, "cell 0 4,alignx trailing");
		
		textCfeC = new JTextField();
		contentPanel.add(textCfeC, "cell 1 4,growx");
		textCfeC.setColumns(10);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JLabel lblNonInt = new JLabel("Non-integer(s)!");
			lblNonInt.setForeground(Color.RED);
			lblNonInt.setVisible(false);
			buttonPane.add(lblNonInt);
			
			JLabel lblClientExist = new JLabel("Client exists!");
			lblClientExist.setForeground(Color.RED);
			lblClientExist.setVisible(false);
			buttonPane.add(lblClientExist);
			
			JLabel lblEmpty = new JLabel("Empty field(s)!");
			lblEmpty.setVisible(false);
			lblEmpty.setForeground(Color.red);
			buttonPane.add(lblEmpty);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						lblEmpty.setVisible(false);
						lblNonInt.setVisible(false);
						lblClientExist.setVisible(false);
						if (isEmpty())
						{
							lblEmpty.setVisible(true);
						}
						else if (isClient())
						{
							lblClientExist.setVisible(true);
						}
						else if (!isIntegers())
						{
							lblNonInt.setVisible(true);
						}
						else
						{
							addClient(textNewCoffee.getText(), textCfeA.getText(), textCfeB.getText(), textCfeC.getText());
							dispose();
						}
					}
				});
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}
	
	//adds client to the client list
	public void addClient(String name, String cfeA, String cfeB, String cfeC)
	{
		Client cli = new Client(name, Integer.parseInt(cfeA), Integer.parseInt(cfeB), Integer.parseInt(cfeC));
		mainList.add(cli);
		MainMenu.fillData(mainTable, mainList);
		
	}
	
	//checks if any text fields are empty
	public boolean isEmpty()
	{
		if (textNewCoffee.getText().contentEquals("") || textCfeA.getText().contentEquals("") || 
			textCfeB.getText().contentEquals("") || textCfeC.getText().contentEquals(""))
			return true;
		return false;
	}
	
	//checks if all text fields storing numbers have integer values
	public boolean isIntegers()
	{
		try
		{
			Integer.parseInt(textCfeA.getText());
			Integer.parseInt(textCfeB.getText());
			Integer.parseInt(textCfeC.getText());
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
	
	//checks if another client in the client list under the same name exists
	public boolean isClient()
	{
		for (Client cli: mainList)
		{
			if (cli.getName().toLowerCase().contentEquals(textNewCoffee.getText().toLowerCase()))
				return true;
		}
		return false;
	}
}
