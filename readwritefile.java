package fileProcessor;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class MultithreadingWriteToFile {
 
	 public static void main(String[] args) {
	        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	        File dir = new File("/home/mohit/Desktop/dir1");
 
	        File[] files = dir.listFiles();
	        //for(int i=0;i<files.length;i++){
	        for (File file : files) {
	    
	        	File destination = new File("/home/mohit/Desktop/dir2/" +file.getName());
	            if(destination.exists()) {
	            	continue;
	            }
	        	Writer w1 = new Writer(file, destination);
	            Thread t = new Thread(w1);
	            t.setPriority(Thread.MAX_PRIORITY);
	            t.start();
 
	        }
 
 
	    }
 
}
 
 
 
class Writer implements Runnable{
File source;
File destination;
   public Writer(File source,File destination) {
this.source = source;
this.destination = destination;
   }
   @Override
   public void run() {
       String content;
       content =   readFromFile(source.getAbsolutePath());
       writeToFile(destination,content);      
   }
 
   private static void writeToFile(File file,String content) {
       try {
           BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
           writer.write(content);
 
           writer.flush();
       } catch (IOException e) {
 
           e.printStackTrace();
       }
 
 
   }
 
   static String readFromFile(String filename){
       StringBuffer content = new StringBuffer();
       try {
           String text;
           BufferedReader reader = new BufferedReader(new FileReader(filename));
               while((text = reader.readLine())!=null){
                   content.append(text);
                   content.append("\n");
 
               }
 
       } catch (FileNotFoundException e) {
 
           e.printStackTrace();
       }
       catch (IOException e) {
 
           e.printStackTrace();
       }
   return content.toString(); 
   }
 
 
}
