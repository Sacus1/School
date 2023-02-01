
abstract class Pizza {
	float price;
	abstract float getCost();
}

class PizzaClassic extends Pizza {
	public PizzaClassic() {
		price = 10;
	}
	float getCost() {
		return price;
	}
}

class PizzaFourre extends Pizza {
	public PizzaFourre() {
		price = 12;
	}
	float getCost() {
		return price;
	}
}

class PizzaFine extends Pizza {
	public PizzaFine() {
		price = 15;
	}
	float getCost() {
		return price;
	}
}

abstract class Ingredient extends Pizza {
	public Pizza pizza;
}

class Cheese extends Ingredient {
	Cheese(Pizza pizza) {
		this.pizza = pizza;
		this.price = 1.0f;
	}
	float getCost() {
		return pizza.getCost() + price;
	}
}

class Tomato extends Ingredient {
	Tomato(Pizza pizza) {
		this.pizza = pizza;
		this.price = 0.5f;
	}
	float getCost() {
		return pizza.getCost() + price;
	}
}

class Dough extends Ingredient {
	Dough(Pizza pizza) {
		this.pizza = pizza;
		this.price = 0.5f;
	}
	float getCost() {
		return pizza.getCost() + price;
	}
}
