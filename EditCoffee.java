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

public class EditCoffee extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JTextField textFieldPrice;
	private JTextField textFieldCost;
	private JTextField textFieldWeight;

	private ArrayList<CoffeeBean> coffeeList;
	private JComboBox coffeeComboBox;
	private JTextField textFieldPriceOld;
	private JTextField textFieldCostOld;
	private JTextField textFieldWeightOld;

	/**
	 * Create the dialog.
	 */
	public EditCoffee(ArrayList<CoffeeBean> list) {
		coffeeList = list;
		
		setBounds(100, 100, 425, 215);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][][][][]"));
		
		coffeeComboBox = new JComboBox(coffeeListToArr(coffeeList));
		coffeeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadQuantities((CoffeeBean)coffeeComboBox.getSelectedItem());
			}
		});
		
		JLabel lblEditCoffee = new JLabel("Edit Coffee");
		contentPanel.add(lblEditCoffee, "cell 0 0 4 1,alignx center");
		JLabel lblCoffeeName = new JLabel("Coffee to edit");
		contentPanel.add(lblCoffeeName, "cell 1 1,alignx trailing");
		contentPanel.add(coffeeComboBox, "cell 2 1 2 1,growx");
		
		JLabel lblPriceOld = new JLabel("Old price");
		contentPanel.add(lblPriceOld, "cell 0 2,alignx trailing");
		
		textFieldPriceOld = new JTextField();
		textFieldPriceOld.setEditable(false);
		contentPanel.add(textFieldPriceOld, "cell 1 2,growx");
		textFieldPriceOld.setColumns(10);
		
		JLabel lblPrice = new JLabel("New price");
		contentPanel.add(lblPrice, "cell 2 2,alignx trailing");
		
		textFieldPrice = new JTextField();
		contentPanel.add(textFieldPrice, "cell 3 2,growx");
		textFieldPrice.setColumns(10);
		
		JLabel lblCostOld = new JLabel("Old cost");
		contentPanel.add(lblCostOld, "cell 0 3,alignx trailing");
		
		textFieldCostOld = new JTextField();
		textFieldCostOld.setEditable(false);
		textFieldCostOld.setColumns(10);
		contentPanel.add(textFieldCostOld, "cell 1 3,growx");
		
		JLabel lblCost = new JLabel("New cost");
		contentPanel.add(lblCost, "cell 2 3,alignx trailing");
		
		textFieldCost = new JTextField();
		contentPanel.add(textFieldCost, "cell 3 3,growx");
		textFieldCost.setColumns(10);
		
		JLabel lblWeightOld = new JLabel("Old weight");
		contentPanel.add(lblWeightOld, "cell 0 4,alignx trailing");
		
		textFieldWeightOld = new JTextField();
		textFieldWeightOld.setEditable(false);
		textFieldWeightOld.setColumns(10);
		contentPanel.add(textFieldWeightOld, "cell 1 4,growx");
		
		JLabel lblWeight = new JLabel("New weight");
		contentPanel.add(lblWeight, "cell 2 4,alignx trailing");
		
		textFieldWeight = new JTextField();
		contentPanel.add(textFieldWeight, "cell 3 4,growx");
		textFieldWeight.setColumns(10);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JLabel lblInvalid = new JLabel("Invalid input!");
			lblInvalid.setForeground(Color.RED);
			buttonPane.add(lblInvalid);
			lblInvalid.setVisible(false);
			
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
						lblInvalid.setVisible(false);
						lblEmptyFields.setVisible(false);
						lblEmptyList.setVisible(false);
						if (isEmpty())
						{
							lblEmptyFields.setVisible(true);
						}
						else if (!isDouble())
						{
							lblInvalid.setVisible(true);
						}
						else if (coffeeList.isEmpty())
						{
							lblEmptyList.setVisible(true);
						}
						else
						{
							editCoffee(textFieldPrice.getText(), textFieldCost.getText(), textFieldWeight.getText());
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
	
	//checks if all text fields have Double inputs
	public boolean isDouble()
	{
		try
		{
			Double.parseDouble(textFieldPrice.getText());
			Double.parseDouble(textFieldCost.getText());
			Double.parseDouble(textFieldWeight.getText());
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
		if (textFieldPrice.getText().contentEquals("") || textFieldCost.getText().contentEquals("") || textFieldWeight.getText().contentEquals(""))
		{
			return true;
		}
		return false;
	}
	
	//edits the CoffeeBean accordingly and updates it in the coffee list
	public void editCoffee(String price, String cost, String weight)
	{
		CoffeeBean coffee = (CoffeeBean)coffeeComboBox.getSelectedItem();
		coffee.setPrice(Double.parseDouble(price));
		coffee.setCost(Double.parseDouble(cost));
		coffee.setWeight(Double.parseDouble(weight));
	}
	
	//loads in the quantities to be edited for the selected CoffeeBean
	public void loadQuantities(CoffeeBean coffee)
	{
		textFieldPriceOld.setText(String.valueOf(coffee.getPrice()));
		textFieldCostOld.setText(String.valueOf(coffee.getCost()));
		textFieldWeightOld.setText(String.valueOf(coffee.getWeight()));
		textFieldPrice.setText(String.valueOf(coffee.getPrice()));
		textFieldCost.setText(String.valueOf(coffee.getCost()));
		textFieldWeight.setText(String.valueOf(coffee.getWeight()));
	}
	
	//converts the coffee list into an array of CoffeeBeans for the combo box to use
	public CoffeeBean[] coffeeListToArr(ArrayList<CoffeeBean> list)
	{
		CoffeeBean[] coffList = new CoffeeBean[list.size()];
		for (int i = 0; i < coffList.length; i++)
		{
			coffList[i] = list.get(i);
		}
		return coffList;
	}

}
