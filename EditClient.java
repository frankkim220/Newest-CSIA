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
import javax.swing.JComboBox;

public class EditClient extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField textFieldCfeA;
	private JTextField textFieldCfeB;
	private JTextField textFieldCfeC;

	private JTable mainTable;
	private ArrayList<Client> mainList;
	private JComboBox nameComboBox;
	private JTextField textFieldCfeAOld;
	private JTextField textFieldCfeBOld;
	private JTextField textFieldCfeCOld;

	/**
	 * Create the dialog.
	 */
	public EditClient(JTable table, ArrayList<Client> list) {
		mainTable = table;
		mainList = list;
		
		setBounds(100, 100, 425, 215);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][][][grow]", "[][][][][][][]"));
		
		nameComboBox = new JComboBox(clientListToArr(mainList));
		nameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadQuantities((Client)nameComboBox.getSelectedItem());
			}
		});
		
		JLabel lblEditClient = new JLabel("Edit Client");
		contentPanel.add(lblEditClient, "cell 0 0 4 1,alignx center");
		JLabel lblClientName = new JLabel("Client to edit");
		contentPanel.add(lblClientName, "cell 1 1,alignx trailing");
		contentPanel.add(nameComboBox, "cell 2 1 2 1,growx");
		
		JLabel lblCoffeeAOld = new JLabel("Coffee A old quantity");
		contentPanel.add(lblCoffeeAOld, "cell 0 2,alignx trailing");
		
		textFieldCfeAOld = new JTextField();
		textFieldCfeAOld.setEditable(false);
		contentPanel.add(textFieldCfeAOld, "cell 1 2,growx");
		textFieldCfeAOld.setColumns(10);
		
		JLabel lblCoffeeANew = new JLabel("Coffee A new quantity");
		contentPanel.add(lblCoffeeANew, "cell 2 2,alignx trailing");
		
		textFieldCfeA = new JTextField();
		contentPanel.add(textFieldCfeA, "cell 3 2,growx");
		textFieldCfeA.setColumns(10);
		
		JLabel lblCoffeeBOld = new JLabel("Coffee B old quantity");
		contentPanel.add(lblCoffeeBOld, "cell 0 3,alignx trailing");
		
		textFieldCfeBOld = new JTextField();
		textFieldCfeBOld.setEditable(false);
		textFieldCfeBOld.setColumns(10);
		contentPanel.add(textFieldCfeBOld, "cell 1 3,growx");
		
		JLabel lblCoffeeBNew = new JLabel("Coffee B new quantity");
		contentPanel.add(lblCoffeeBNew, "cell 2 3,alignx trailing");
		
		textFieldCfeB = new JTextField();
		contentPanel.add(textFieldCfeB, "cell 3 3,growx");
		textFieldCfeB.setColumns(10);
		
		JLabel lblCoffeeCOld = new JLabel("Coffee C old quantity");
		contentPanel.add(lblCoffeeCOld, "cell 0 4,alignx trailing");
		
		textFieldCfeCOld = new JTextField();
		textFieldCfeCOld.setEditable(false);
		textFieldCfeCOld.setColumns(10);
		contentPanel.add(textFieldCfeCOld, "cell 1 4,growx");
		
		JLabel lblCoffeeCNew = new JLabel("Coffee C new quantity");
		contentPanel.add(lblCoffeeCNew, "cell 2 4,alignx trailing");
		
		textFieldCfeC = new JTextField();
		contentPanel.add(textFieldCfeC, "cell 3 4,growx");
		textFieldCfeC.setColumns(10);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JLabel lblNonInt = new JLabel("Non-integer(s)!");
			lblNonInt.setForeground(Color.RED);
			buttonPane.add(lblNonInt);
			lblNonInt.setVisible(false);
			
			JLabel lblEmptyList = new JLabel("Empty list!");
			lblEmptyList.setForeground(Color.RED);
			buttonPane.add(lblEmptyList);
			lblEmptyList.setVisible(false);
			
			
			JLabel lblEmptyFields = new JLabel("Empty field(s)!");
			lblEmptyFields.setForeground(Color.RED);
			buttonPane.add(lblEmptyFields);
			lblEmptyFields.setVisible(false);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						lblNonInt.setVisible(false);
						lblEmptyFields.setVisible(false);
						lblEmptyList.setVisible(false);
						if (isEmpty())
						{
							lblEmptyFields.setVisible(true);
						}
						else if (!isIntegers())
						{
							lblNonInt.setVisible(true);
						}
						else if (mainList.isEmpty())
						{
							lblEmptyList.setVisible(true);
						}
						else
						{
							editClient(textFieldCfeA.getText(), textFieldCfeB.getText(), textFieldCfeC.getText());
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
	
	//checks if all text fields storing numbers have integer values
	public boolean isIntegers()
	{
		try
		{
			Integer.parseInt(textFieldCfeA.getText());
			Integer.parseInt(textFieldCfeB.getText());
			Integer.parseInt(textFieldCfeC.getText());
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}
	
	//checks if any text fields are empty
	public boolean isEmpty()
	{
		if (textFieldCfeA.getText().contentEquals("") || textFieldCfeB.getText().contentEquals("") || textFieldCfeC.getText().contentEquals(""))
		{
			return true;
		}
		return false;
	}
	
	//edits client accordingly with the new information and updates the client in the client list
	public void editClient(String cfeA, String cfeB, String cfeC)
	{
		Client cli = (Client)nameComboBox.getSelectedItem();
		cli.setA(Integer.parseInt(cfeA));
		cli.setB(Integer.parseInt(cfeB));
		cli.setC(Integer.parseInt(cfeC));
		MainMenu.fillData(mainTable, mainList);
	}
	
	//loads in the quantities to be edited for the selected client
	public void loadQuantities(Client cli)
	{
		textFieldCfeAOld.setText(String.valueOf(cli.getA()));
		textFieldCfeBOld.setText(String.valueOf(cli.getB()));
		textFieldCfeCOld.setText(String.valueOf(cli.getC()));
		textFieldCfeA.setText(String.valueOf(cli.getA()));
		textFieldCfeB.setText(String.valueOf(cli.getB()));
		textFieldCfeC.setText(String.valueOf(cli.getC()));
	}
	
	//converts the client list into an array of clients for the combo box to use
	public Client[] clientListToArr(ArrayList<Client> list)
	{
		Client[] cliList = new Client[list.size()];
		for (int i = 0; i < cliList.length; i++)
		{
			cliList[i] = list.get(i);
		}
		return cliList;
	}
}
