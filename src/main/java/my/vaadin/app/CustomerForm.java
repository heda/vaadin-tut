package my.vaadin.app;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerForm extends FormLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// form elements
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private TextField email = new TextField("Email");
	private NativeSelect<CustomerStatus> status = new NativeSelect<>("Status");
	private DateField birthdate = new DateField("Birthday");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private Binder<Customer> binder = new Binder<>(Customer.class);
	// needed references
	private CustomerService service = CustomerService.getInstance();
	private Customer customer;
	private MyUI myUI;
	
	public CustomerForm(MyUI myUI) {
	    this.myUI = myUI;
	    // add listeners to btns
	    save.addClickListener(e -> this.save());
	    delete.addClickListener(e -> this.delete());
	    // bind field to object
	    binder.bindInstanceFields(this);
	    
	    // populate options for status select
	    status.setItems(CustomerStatus.values());
	    
	    // style save tn
	    save.setStyleName(ValoTheme.BUTTON_PRIMARY);
	    save.setClickShortcut(KeyCode.ENTER);

	    setSizeUndefined();
	    HorizontalLayout buttons = new HorizontalLayout(save, delete);
	    addComponents(firstName, lastName, email, status, birthdate, buttons);
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	    binder.setBean(customer);

	    // Show delete button for only customers already in the database
	    delete.setVisible(customer.isPersisted());
	    setVisible(true);
	    firstName.selectAll();
	}
	
	private void delete() {
	    service.delete(customer);
	    myUI.updateList();
	    setVisible(false);
	}

	private void save() {
	    service.save(customer);
	    myUI.updateList();
	    setVisible(false);
	}

}
