package vo;

import java.io.Serializable;

public class Maint_detail_key implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7362087010348419899L;
	private int maint_id;
	private int maint_item_id;
	public int getMaint_id() {
		return maint_id;
	}
	public void setMaint_id(int maint_id) {
		this.maint_id = maint_id;
	}
	public int getMaint_item_id() {
		return maint_item_id;
	}
	public void setMaint_item_id(int maint_item_id) {
		this.maint_item_id = maint_item_id;
	}
	@Override  
    public boolean equals(Object o) {  
        if(o instanceof Maint_detail_key){  
        	Maint_detail_key key = (Maint_detail_key)o ;  
            if(this.maint_id == key.getMaint_id() && this.maint_item_id==key.getMaint_item_id()){  
                return true ;  
            }  
        }  
        return false ;  
    }  
      
    @Override  
    public int hashCode() {  
        return super.hashCode();
    }  

}
