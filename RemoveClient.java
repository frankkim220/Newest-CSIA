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

public class RemoveClient extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JTable mainTable;
	private ArrayList<Client> mainList;
	private Client[] clientList;
	private JComboBox nameComboBox;
	private JTextField textFieldCoffeeA;
	private JTextField textFieldCoffeeB;
	private JTextField textFieldCoffeeC;

	/**
	 * Create the dialog.
	 */
	public RemoveClient(JTable table, ArrayList<Client> list) {
		mainTable = table;
		mainList = list;
		clientList = clientListToArr(mainList);
		
		setBounds(100, 100, 425, 215);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][][]"));
		
		JLabel lblRemoveClient = new JLabel("Remove Client");
		contentPanel.add(lblRemoveClient, "cell 0 0 2 1,alignx center");
		JLabel lblClientName = new JLabel("Client to remove");
		contentPanel.add(lblClientName, "cell 0 1,alignx trailing");
		
		nameComboBox = new JComboBox(clientList);
		nameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadQuantities((Client)nameComboBox.getSelectedItem());
			}
		});
		contentPanel.add(nameComboBox, "cell 1 1,growx");
		
		JLabel lblCoffeeA = new JLabel("Coffee A quantity");
		contentPanel.add(lblCoffeeA, "cell 0 2,alignx trailing");
		
		textFieldCoffeeA = new JTextField();
		textFieldCoffeeA.setEditable(false);
		contentPanel.add(textFieldCoffeeA, "cell 1 2,growx");
		textFieldCoffeeA.setColumns(10);
		
		JLabel lblCoffeeB = new JLabel("Coffee B quantity");
		contentPanel.add(lblCoffeeB, "cell 0 3,alignx trailing");
		
		textFieldCoffeeB = new JTextField();
		textFieldCoffeeB.setEditable(false);
		textFieldCoffeeB.setColumns(10);
		contentPanel.add(textFieldCoffeeB, "cell 1 3,growx");
		
		JLabel lblCoffeeC = new JLabel("Coffee C quantity");
		contentPanel.add(lblCoffeeC, "cell 0 4,alignx trailing");
		
		textFieldCoffeeC = new JTextField();
		textFieldCoffeeC.setEditable(false);
		textFieldCoffeeC.setColumns(10);
		contentPanel.add(textFieldCoffeeC, "cell 1 4,growx");
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JLabel lblEmptyList = new JLabel("Empty list!");
			lblEmptyList.setForeground(Color.RED);
			lblEmptyList.setVisible(false);
			buttonPane.add(lblEmptyList);
			{	
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						lblEmptyList.setVisible(false);
						if (mainList.isEmpty())
						{
							lblEmptyList.setVisible(true);
						}
						else
						{
							deleteClient(nameComboBox.getSelectedItem().toString());
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
	
	//removes client from the client list
	public void deleteClient(String name)
	{
		Client cli = (Client)nameComboBox.getSelectedItem();
		mainList.remove(cli);
		MainMenu.fillData(mainTable, mainList);
	}
	
	//loads in the quantities for the selected client
	public void loadQuantities(Client cli)
	{
		textFieldCoffeeA.setText(String.valueOf(cli.getA()));
		textFieldCoffeeB.setText(String.valueOf(cli.getB()));
		textFieldCoffeeC.setText(String.valueOf(cli.getC()));
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
