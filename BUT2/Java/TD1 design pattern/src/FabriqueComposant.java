abstract class FabriqueComposant {
	abstract void createButton();
	abstract void createMenu();
	abstract void createBarre();
}
abstract class Button {
}
abstract class Menu {
}
abstract class Barre {
}
class ButtonWindows extends Button {
	public ButtonWindows() {
	}
}
class MenuWindows extends Menu {
	public MenuWindows() {
	}
}
class BarreWindows extends Barre {
	public BarreWindows() {
	}
}
class FabriqueComposantWindows extends FabriqueComposant {
	public FabriqueComposantWindows() {
	}
	public void createButton() {
		new ButtonWindows();
	}
	public void createMenu() {
		new MenuWindows();
	}
	public void createBarre() {
		new BarreWindows();
	}
}
class ButtonLinux extends Button {
	public ButtonLinux() {
	}
}
class MenuLinux extends Menu {
	public MenuLinux() {
	}
}
class BarreLinux extends Barre {
	public BarreLinux() {
	}
}
class FabriqueComposantLinux extends FabriqueComposant {
	public FabriqueComposantLinux() {
	}
	public void createButton() {
		new ButtonLinux();
	}
	public void createMenu() {
		new MenuLinux();
	}
	public void createBarre() {
		new BarreLinux();
	}
}
class ButtonMac extends Button {
	public ButtonMac() {
	}
}
class MenuMac extends Menu {
	public MenuMac() {
	}
}

class BarreMac extends Barre {
	public BarreMac() {
	}
}
class FabriqueComposantMac extends FabriqueComposant {
	public FabriqueComposantMac() {
	}
	public void createButton() {
		new ButtonMac();
	}
	public void createMenu() {
		new MenuMac();
	}
	public void createBarre() {
		new BarreMac();
	}
}
