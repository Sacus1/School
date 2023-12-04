import java.util.ArrayList;
import java.util.List;

abstract class ElementXML {
  public List<ElementXML> children;

  abstract void operation();

  abstract void add(ElementXML c);

  abstract void remove(ElementXML c);

  abstract ElementXML getChild(int i);
}

class ElementXMLComposite extends ElementXML {
  public ElementXMLComposite() {
    children = new ArrayList<ElementXML>();
  }

  public void operation() {
    for (ElementXML child : children) {
      child.operation();
    }
  }

  public void add(ElementXML c) {
    children.add(c);
  }

  public void remove(ElementXML c) {
    children.remove(c);
  }

  public ElementXML getChild(int i) {
    return children.get(i);
  }
}

class ElementXMLSimple extends ElementXML {
  public String content;

  public void operation() {
    System.out.println("ElementXMLSimple.operation()");
  }

  public void add(ElementXML c) {
    throw new UnsupportedOperationException();
  }

  public void remove(ElementXML c) {
    throw new UnsupportedOperationException();
  }

  public ElementXML getChild(int i) {
    throw new UnsupportedOperationException();
  }
}
