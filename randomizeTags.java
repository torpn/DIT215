package dit215;



public class randomizeTags {

	public class randomize_tags {
		 public java.util.Random rand = new java.util.Random();
		
		 //from integer from 0-99
		 public int randomPrice(){
		    
		      return rand.nextInt(100);
		 }
		 
		 //just random characters
		 public String randomFood() {   
		        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
		        StringBuffer sb = new StringBuffer();   
		        int L = 5;  
		        int range = buffer.length();   
		        for (int i = 0; i < L; i ++) {   
		            sb.append(buffer.charAt(rand.nextInt(range)));   
		        }   
		        return sb.toString();   
		    }  

		 //just random characters
		 public String randomAtmosphere(int length) {   
		        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
		        StringBuffer sb = new StringBuffer();   
		          
		        int range = buffer.length();   
		        for (int i = 0; i < length; i ++) {   
		            sb.append(buffer.charAt(rand.nextInt(range)));   
		        }   
		        return sb.toString();   
		    }  
		  
	}
}
