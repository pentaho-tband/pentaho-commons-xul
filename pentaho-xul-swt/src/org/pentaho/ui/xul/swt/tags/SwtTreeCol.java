package org.pentaho.ui.xul.swt.tags;

import java.util.List;

import javax.swing.table.TableColumnModel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.binding.BindingUtil;
import org.pentaho.ui.xul.binding.InlineBindingExpression;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.ColumnType;
import org.pentaho.ui.xul.util.SortDirection;

public class SwtTreeCol extends SwtElement implements XulTreeCol {

    private String label;
    private ColumnType type = ColumnType.TEXT;
    private boolean editable = false;
    private TableColumnModel model;
    private String binding = "";
    private String comboBinding;
    private String bindingChildrenProperty;
    private String columnTypeBinding;
    private String disabledBinding;
    private String image;
    private boolean expanded;
    private SortDirection sortDirection;
    private boolean sortActive = false;
    private String expandedBinding;
    private String tooltipBinding;
    
    public SwtTreeCol(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
      super("treecol");
      setManagedObject("empty");
    }

    public void autoSize() {
      // TODO Auto-generated method stub
      
    }

    public ColumnType getColumnType() {
      return this.type;
    }

    public String getLabel() {
      return label;
    }

    public String getSortDirection() {
      return sortDirection.toString();
    }
    
    public SortDirection getSortDir(){
      return sortDirection;
    }

    public String getSrc() {
      // TODO Auto-generated method stub
      return null;
    }

    public String getType() {
      return this.type.toString();
    }

    public int getWidth() {
      // TODO Auto-generated method stub
      return 0;
    }

    public boolean isEditable() {
      return this.editable;
    }

    public boolean isFixed() {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isHidden() {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isPrimary() {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isSortActive() {
      return sortActive;
    }

    public void setEditable(boolean edit) {
      this.editable = edit;
    }

    public void setFixed(boolean fixed) {
      // TODO Auto-generated method stub
      
    }

    public void setHidden(boolean hide) {
      // TODO Auto-generated method stub
      
    }

    public void setLabel(String label) {
      this.label = label;
      
    }

    public void setPrimary(boolean primo) {
      // TODO Auto-generated method stub
      
    }

    public void setSortActive(boolean sort) {
      sortActive = sort;
    }

    public void setSortDirection(String dir) {
      sortDirection = SortDirection.valueOf(dir);
    }

    public void setSrc(String srcUrl) {
      // TODO Auto-generated method stub
      
    }
;
    public void setType(String type) {
      try{
        this.type = ColumnType.valueOf(type.toUpperCase());
      } catch(Exception e){
        this.type = ColumnType.CUSTOM;
      }
    }

    public void setWidth(int width) {
      // TODO Auto-generated method stub
      
    }

    public String getCustomclass() {
      // TODO Auto-generated method stub
      return null;
    }

    public void setCustomclass(String customClass) {
      // TODO Auto-generated method stub
      
    }

    public String getCustomeditor() {
      // TODO Auto-generated method stub
      return null;
    }

    public void setCustomeditor(String customClass) {
      // TODO Auto-generated method stub
      
    }

    public List<InlineBindingExpression> getBindingExpressions() {
      return BindingUtil.getBindingExpressions(binding);
    }

    public void setBinding(String binding) {
      this.binding = binding;
    }
    
    public String getBinding() {
      return binding;
    }
    

    public String getChildrenbinding() {
      return bindingChildrenProperty;
    }

    public void setChildrenbinding(String childProperty) {
      this.bindingChildrenProperty = childProperty;  
    }

    public String getCombobinding() {
      return comboBinding;
    }

    public void setCombobinding(String property) {
      this.comboBinding = property;  
    }


    public void setColumntypebinding(String propertyName){
      this.columnTypeBinding = propertyName;
    }

    public String getColumntypebinding(){
      return this.columnTypeBinding;
    }

    public String getDisabledbinding() {
      return disabledBinding;
    }

    public void setDisabledbinding(String property) {
      this.disabledBinding = property;
    }

    public String getImagebinding() {
      return image;
    }

    public void setImagebinding(String img) {
      this.image = img;
    }

    public String getExpandedbinding() {
      return expandedBinding;
    }

    public void setExpandedbinding(String bind) {
      this.expandedBinding = bind;
    }

    public String getTooltipbinding() {
      return tooltipBinding;
    }

    public void setTooltipbinding(String bind) {
      this.tooltipBinding = bind;      
    }
    
    
    
  }
