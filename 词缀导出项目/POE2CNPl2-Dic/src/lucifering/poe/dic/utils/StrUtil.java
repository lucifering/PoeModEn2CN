package lucifering.poe.dic.utils;

import java.util.ArrayList;
import java.util.List;

public class StrUtil {
	
	
	public static String[] NumStr=new String[] {
			 
			"%1%","%2%","%3%","%4%","%5%","%6%","%7%","%8%","%9%","%10%","%11%","%12%" 
			 
			 
	};
	
	 public static boolean isIn(String str) {
		 
		 List<String> list=new ArrayList<String> ();
		 for(int i=0;i<8;i++) {
			 
			 list.add(i+"");
		 }
		 
		 if(list.contains(str.trim())) {
			 return true;
		 }
		 return false;
		 
	 }
	 
	 
 public static String doEnPre(String en,boolean cut ) {
		 
		 for(String str:NumStr) {
			 
			 
			 en=en.replaceAll("%%" , "%");
			 
			 
			 while(en.contains("%1$d")) {
				 en=en.replace("%1$d" ,  "(\\\\d+|(\\\\d+\\\\.\\\\d+))");
			 }
			
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "(\\\\-|\\\\+?)(\\\\d+|(\\\\d+\\.\\\\d+))");
			 }
			 while(en.contains("%2$d")) {
				 en=en.replace("%2$d" ,  "(\\\\d+|(\\\\d+\\\\.\\\\d+))");
			 }
			
			
			 while(en.contains("+"+str)) {
				 en=en.replace("+"+str ,"\\\\+(\\\\d+|(\\\\d+\\\\.\\\\d+))");
			 }
			 
			 
			 while(en.contains(str)) {
				 en=en.replace(str ,   "(\\\\d+|(\\\\d+\\\\.\\\\d+))");
			 }
			 
			 
		 }
		 if(cut && en.startsWith("+")) {
			 en=en.substring(1, en.length());
		 }
		 
		 for(int i=1;i<10;i++) {
			 while(en.contains("+"+i)) {
				 en=en.replace("+"+i ,  "【ADD】"+i);
			 }
			 
		 }

		 for(int i=1;i<10;i++) {
			 while(en.contains("【ADD】"+i)) {
				 en=en.replace("【ADD】"+i ,  "\\\\+"+i);
			 }
			 
		 }
		 return en;
			
		}
 
 
	 public static String enPre(String en,boolean cut ) {
		 
		 for(String str:NumStr) {
			 
			 
			 en=en.replaceAll("%%" , "%");
			 
			 
			 while(en.contains("%1$d")) {
				 en=en.replace("%1$d" ,  "(\\d+|(\\d+\\.\\d+))");
			 }
			
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "(\\-|\\+?)(\\d+|(\\d+\\.\\d+))");
			 }
			 while(en.contains("%2$d")) {
				 en=en.replace("%2$d" ,  "(\\d+|(\\d+\\.\\d+))");
			 }
			
			 en=en.replaceAll("\\+"+str , "\\\\+(\\\\d+|(\\\\d+\\\\.\\\\d+))");
			 
			 en=en.replaceAll(str , "(\\\\d+|(\\\\d+\\\\.\\\\d+))");
		 }
		 if(cut && en.startsWith("+")) {
			 en=en.substring(1, en.length());
		 }
		
		 
		 
		 return en;
			
		}
	 
public static String cnPre(String en) {
		 
		 for(String str:NumStr) {
			 en=en.replaceAll("%%" , "%");
			
			 while(en.contains("%1$d")) {
				 en=en.replace("%1$d" ,  "#");
			 }
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "+#");
			 }
			 while(en.contains("%2$d")) {
				 en=en.replace("%2$d" ,  "#");
			 }
			 en=en.replaceAll("\\+"+str , "+#");
			 
			 en=en.replaceAll(str , "#");
		 }
		 
		 return en;
			
		}



public static String enPreFormatString(String en,boolean add) {
	 
	 for(String str:NumStr) {
		 en=en.replaceAll("%%" , "%");
		 
		 
		 if(add) {
			 
			 while((en.contains("%1$+d"))) {
					en=en.replace("%1$+d" , "+【~】");
				}
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "+【~】");
			 }
		 }else {
			 while((en.contains("%1$+d"))) {
					en=en.replace("%1$+d" , "-【~】");
				}
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "-【~】");
			 }
		 }
 
		
		 
		 while(en.contains("%1$d")) {
			 en=en.replace("%1$d" ,  "【~】");
		 }
		
		 
		 while(en.contains("%2$d")) {
			 en=en.replace("%2$d" ,  "【~】");
		 }
		
		 en=en.replaceAll("\\+"+str , "+【~】");
		 
		 en=en.replaceAll(str , "【~】");
	 }
	 int index=0;
	  while(en.contains("【~】")) {
			 en=en.replaceFirst("【~】" ,  "{"+index+"}");
			 index=index+1;
		 }
	 
	 return en;
		
	}



public static String cnPreFormatString(String en,boolean add) {
	 
	 for(String str:NumStr) {
		 en=en.replaceAll("%%" , "%");
		 
		 if(add) {
			 
			 while((en.contains("%1$+d"))) {
					en=en.replace("%1$+d" , "+【~】");
				}
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "+【~】");
			 }
		 }else {
			 while((en.contains("%1$+d"))) {
					en=en.replace("%1$+d" , "-【~】");
				}
			 while(en.contains("%2$+d")) {
				 en=en.replace("%2$+d" , "-【~】");
			 }
		 }
			 
		  
			
			
			
		 
		 while(en.contains("%1$d")) {
			 en=en.replace("%1$d" ,  "【~】");
		 }
		
		 while(en.contains("%2$d")) {
			 en=en.replace("%2$d" ,  "【~】");
		 }
		 
		 
		 en=en.replaceAll("\\+"+str , "+【~】");
		 en=en.replaceAll("\\-"+str , "-【~】");
		 en=en.replaceAll(str , "【~】");
	 }
	 int index=0;
	  while(en.contains("【~】")) {
			 en=en.replaceFirst("【~】" ,  "{"+index+"}");
			 index=index+1;
	  }
	  
	 return en;
		
	}




	 
}
