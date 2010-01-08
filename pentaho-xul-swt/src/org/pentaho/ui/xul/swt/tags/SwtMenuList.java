package org.pentaho.ui.xul.swt.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

//TODO: Move creation of combobox to late initialization to support switching from editable to non-editable.
public class SwtMenuList<T> extends AbstractSwtXulContainer implements XulMenuList<T> {

  private Combo combobox;

  private XulDomContainer xulDomContainer;

  private static final Log logger = LogFactory.getLog(SwtMenuList.class);

  private boolean loaded = false;

  private String binding;

  private T previousSelectedItem = null;
  
  private SwtMenupopup popup;
  
  private SwtMenuitem selectedItem = null;

  private boolean editable = false;
  
  private String command;

  private XulComponent parent;
  public SwtMenuList(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menulist");

    this.xulDomContainer = domContainer;
    this.parent = parent;
    setupCombobox();

  }


  private void setupCombobox(){


    if(editable){
      combobox = new Combo((Composite)parent.getManagedObject(), SWT.DROP_DOWN);
    } else {
      combobox = new Combo((Composite)parent.getManagedObject(), SWT.DROP_DOWN | SWT.READ_ONLY);
    }

    setManagedObject(combobox);

    combobox.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {

      public void widgetSelected(SelectionEvent e) {
        SwtMenuList.this.changeSupport.firePropertyChange("selectedItem",
            previousSelectedItem, combobox.getItem(combobox.getSelectionIndex())
        );
        SwtMenuList.this.changeSupport.firePropertyChange("selectedIndex", null
            , combobox.getSelectionIndex());

        previousSelectedItem = (T) combobox.getItem(combobox.getSelectionIndex());
        
        if(SwtMenuList.this.command != null){
          invoke(SwtMenuList.this.command, new Object[] {});
        }

      }

    });
  }
  

  @Override
  public void addChild(Element ele) {
    super.addChild(ele);
    if(ele instanceof XulMenupopup){
      popup = (SwtMenupopup) ele;
    }
  }


  public void layout() {
    combobox.removeAll();
    selectedItem = null; //clear selection
    
    for (XulComponent item : popup.getChildNodes()) {
      SwtMenuitem mItem = (SwtMenuitem) item;
      if(mItem.isSelected()){
        this.selectedItem = mItem;
      }
      combobox.add(mItem.getLabel());
    }
    int idx = -1;
    if(selectedItem != null){
      idx = combobox.indexOf(selectedItem.toString());
    } else if( popup.getChildNodes().size() > 0){
      idx = 0;
    }
    this.setSelectedIndex(idx);
    
    loaded = true;
  }

  /*
   * Swaps out the managed list.  Effectively replaces the SwingMenupopup child component.
   * (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulMenuList#replaceAll(java.util.List)
   */
  @Deprecated
  public void replaceAllItems(Collection<T> tees) {
    setElements(tees);
  }

  public String getSelectedItem() {
    int idx = combobox.getSelectionIndex();
    return (idx > -1 && idx < this.combobox.getItemCount())? this.combobox.getItem(idx) : null;
  }

  public void setSelectedItem(T t) {
    this.previousSelectedItem = null;
    this.combobox.select(combobox.indexOf(t.toString()));
  }

  public void setOncommand(final String command) {
    this.command = command;
  }

  public Collection<T> getElements() {
    return (Collection) popup.getChildNodes();
  }

  public String getBinding() {
    return binding;
  }

  public void setBinding(String binding) {
    this.binding = binding;
  }

  private String extractLabel(T t) {
    String attribute = getBinding();
    if (StringUtils.isEmpty(attribute)) {
      return t.toString();
    } else {
      String getter = "get" + (String.valueOf(attribute.charAt(0)).toUpperCase()) + attribute.substring(1);
      try {
        return new Expression(t, getter, null).getValue().toString();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void setElements(Collection<T> tees) {
    for (XulComponent menuItem : popup.getChildNodes()) {
      popup.removeChild(menuItem);
    }

    if(tees == null){
      return;
    }
    for (T t : tees) {
      try{
        XulMenuitem item = (XulMenuitem) xulDomContainer.getDocumentRoot().createElement("menuitem");

        String attribute = getBinding();
        item.setLabel(extractLabel(t));
  
        popup.addChild(item);
      } catch(XulException e){
        logger.error("Unable to create new menulist menuitem: ", e);
      }
    }

    layout();
  }
  public int getSelectedIndex() {
    return this.combobox.getSelectionIndex();
  }

  public void setSelectedIndex(int idx) {
    if(idx == -1){
      this.combobox.clearSelection();
    } else {
      this.combobox.select(idx);
    }
    if(idx >= 0){
      changeSupport.firePropertyChange("selectedItem",
          previousSelectedItem, combobox.getItem(combobox.getSelectionIndex())
      );
    }
    changeSupport.firePropertyChange("selectedIndex", null
        , combobox.getSelectionIndex());
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public boolean getEditable() {
    return editable;
  }

  public String getValue() {
    return getSelectedItem();
  }

  public void setValue(String value) {
    combobox.setText(value);
  }


  @Override
  public void setDisabled(boolean disabled) {
    super.setDisabled(disabled);
    combobox.setEnabled(!disabled);
  }
  
  
}
