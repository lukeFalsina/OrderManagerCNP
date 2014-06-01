package view;

//import java.util.ArrayList;
import java.util.List;

import main.ControllerListener;
import main.ViewListener;

import model.Element;
//import model.Food;
import model.Supplement;

public abstract class AbstractView implements ViewListener {

	protected ControllerListener controllerListener;
	protected List<Element> elementList;
	protected boolean enableSupplementUse;
	protected List<Supplement> supplementList;

	public AbstractView(ControllerListener controllerListener, List<Element> elementList) {
		this.controllerListener = controllerListener;
		this.elementList = elementList;
/*		this.elementList = new ArrayList<Element>();
		this.elementList.add(new Food("Pasta al sugo", 3.5f, "Pasta e Basta", 0l));
		this.elementList.add(new Food("Pasta al pesto", 3f, "Pasta e Basta", 0l));
		this.elementList.add(new Food("Costine", 4f, "Maial", 0l));
		this.elementList.add(new Food("Spiedino", 4f, "Spiedì", 0l));
		this.elementList.add(new Food("Patatine", 1.5f, "Pata Fabio", 0l));
*/		
		this.enableSupplementUse = false;
	}
}
