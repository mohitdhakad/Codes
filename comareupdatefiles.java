package fileProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.Thread;
import java.util.concurrent.TimeUnit;
 
public class MultithreadingWriteToFile {
	private static boolean isEqual(Path firstFile, Path secondFile) {
        try {
            long size = Files.size(firstFile);
            if (size != Files.size(secondFile)) {
                return false;
            }
 
            if (size < 2048) {
                return Arrays.equals(Files.readAllBytes(firstFile),
                            Files.readAllBytes(secondFile));
            }
 
            // Compare line-by-line
            try (BufferedReader bf1 = Files.newBufferedReader(firstFile);
                 BufferedReader bf2 = Files.newBufferedReader(secondFile)) {
 
                String line;
                while ((line = bf1.readLine()) != null) {
                    if (line != bf2.readLine()) {
                        return false;
                    }
                }
            }
 
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
 
 public static void main(String[] args){
   Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
   while (true) {
     try {
       Thread.sleep(1000);
     } catch (Exception e) {
       System.out.println(e.toString());
     }
     File dir = new File("/home/mohit/Desktop/dir1");
     File[] files = dir.listFiles();
     for (File file : files) {
       File destination = new File("/home/mohit/Desktop/dir2/" + file.getName());
       if (destination.exists() && isEqual(file.toPath(),destination.toPath())) {
         continue;
       }
       Writer w1 = new Writer(file, destination);
       Thread t = new Thread(w1);
       t.setPriority(Thread.MAX_PRIORITY);
       t.start();
     }
       }
 }
}
 
class Writer implements Runnable {
 File source;
 File destination;
 
 public Writer(File source, File destination) {
   this.source = source;
   this.destination = destination;
 }
 
 @Override
 public void run() {
   String content;
   content = readFromFile(source.getAbsolutePath());
   writeToFile(destination, content);
 }
 
 private static void writeToFile(File file, String content) {
   try {
     BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
 
     writer.write(content);
     writer.flush();
   } catch (IOException e) {
     e.printStackTrace();
   }
 }
 
 static String readFromFile(String filename) {
   StringBuffer content = new StringBuffer();
   try {
     String text;
     BufferedReader reader = new BufferedReader(new FileReader(filename));
     while ((text = reader.readLine()) != null) {
       content.append(text);
       content.append("\n");
     }
   } catch (FileNotFoundException e) {
     e.printStackTrace();
   } catch (IOException e) {
     e.printStackTrace();
   }
   return content.toString();
 }
}
