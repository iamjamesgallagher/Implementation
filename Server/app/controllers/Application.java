package controllers;

import models.GradeLevel;
import models.Test;
import models.GradePointAverage;
import models.Hobby;
import models.Major;
import models.Student;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.formdata.StudentFormData;
import views.html.*;

import models.Stock;
import models.TimePoint;
import java.util.Date;
import java.util.ArrayList;


import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/*for example form */

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.data.validation.Constraints.*;

import views.html.*;

//Upload a file 
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

//runnning uloaded jar
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.lang.reflect.Constructor;

//db
import java.util.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.data.validation.Constraints.*;

import views.html.*;

import models.*;

public class Application extends Controller {

  public static class Hello {
        @Required public  Integer startHour;
        @Required public Integer startMin;
         @Required public  Integer endHour;
         @Required public  Integer  endMin;
         @Required public  Integer amount;
        @Required  public Integer period;
    } 

       public static Result sayHello() {

        Stock appl = data("C:\\Users\\User\\Desktop\\Implementation\\Server\\public\\files\\Test_Data.txt");
          Form<Hello> form = form(Hello.class).bindFromRequest();
        

        

        if(form.hasErrors()) {

          System.out.println("message : "+form.errors());

          return  TODO ; /*TWAPupload.render(  TWAPFunc(  appl,start, end,data.amount, data.period )     );*/

        } else {

          Hello data = form.get();

            

         Calendar start = Calendar.getInstance();
              start.set(Calendar.HOUR_OF_DAY,data.startHour);
              start.set(Calendar.MINUTE,data.startMin);
              start.set(Calendar.SECOND,0);
              start.set(Calendar.MILLISECOND,0);        

              
              Calendar end = Calendar.getInstance();

              int hour ;
              if (data.endHour < 12){ 
                hour= (data.endHour +12);
              } else {hour =data.endHour;}

              end.set(Calendar.HOUR_OF_DAY,hour );
              end.set(Calendar.MINUTE,data.endMin);
              end.set(Calendar.SECOND,0);
              end.set(Calendar.MILLISECOND,0);
          
            return ok(
              
                TWAP.render(  TWAPFunc(  appl,start, end,data.amount, data.period )  , form(Hello.class)    )
            );

        }      //else


            
        }
        

       /* public static Result sayHello() {
            return TODO ;}*/



  public static Result TWAP() {
    Calendar start = Calendar.getInstance();
    start.set(Calendar.HOUR_OF_DAY,10);
    start.set(Calendar.MINUTE,30);
    start.set(Calendar.SECOND,0);
    start.set(Calendar.MILLISECOND,0);        
    Calendar end = Calendar.getInstance();
    end.set(Calendar.HOUR_OF_DAY,14);
    end.set(Calendar.MINUTE,30);
    end.set(Calendar.SECOND,0);
    end.set(Calendar.MILLISECOND,0);

    Stock appl = data("C:\\Users\\User\\Desktop\\Implementation\\Server\\public\\files\\Test_Data.txt");

    /*ArrayList buytime= buyTimes(start, end,30);*/

    return ok(
       TWAPnodatapoints.render(TWAPFunc(appl, start, end,800, 30), form(Hello.class))
    );

    }






















  public static Stock data(String f) {
    Stock appl = null;

    try {
      Scanner fileIn = new Scanner(new File(f));
      fileIn.useDelimiter(",");  DateFormat dateFormat = new SimpleDateFormat("HH:mm");
      Calendar cal = Calendar.getInstance(); cal.set(Calendar.HOUR_OF_DAY,9); 
      cal.set(Calendar.MINUTE,30); cal.set(Calendar.SECOND,0); 
      cal.set(Calendar.MILLISECOND,0);    
      /*   System.out.println(dateFormat.format(cal.getTime()));*/

      appl = new Stock("Appl");

      while (fileIn.hasNextLine() == true){            
      //COLUMNS=DATE,CLOSE,HIGH,LOW,OPEN,VOLUME System.out.println(fileIn.nextLine());      
        String singleLine = fileIn.nextLine();     String[] lineArray = singleLine.split(",");
        Float price = Float.parseFloat(lineArray[2]);  Float volume = Float.parseFloat(lineArray[5]);      
        Float high = Float.parseFloat(lineArray[2]);  Float open = Float.parseFloat(lineArray[4]);
        Float low = Float.parseFloat(lineArray[3]);  Float close = Float.parseFloat(lineArray[1]);

        Date myDate = cal.getTime();  
        TimePoint ts = new TimePoint(myDate.getTime() , cal.getTime(),price,volume,high, low,close, open); 
        cal.add(Calendar.MINUTE, 1);
        appl.setTimeandprice(ts);     

        /*System.out.println( myDate.getTime());*/      
      } 

    } catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
      e.printStackTrace();  }

      return appl;

    } 





 public static Stock TWAPFunc(Stock s, Calendar start, Calendar end, int amount, int period){
    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    double totalCost = 0;
    double totalBought = 0;

    ArrayList buytime = buyTimes(start, end, period);

    double numberOfOrders = buytime.size() ;
    //    
    double indivOrderSizeDec = amount/numberOfOrders;     
    int indivOrderSize = (int)indivOrderSizeDec;

    for (int x = 0 ; x < s.DataLength(); x++){
      Date testd = s.getDataPoint(x).getTime(); /*System.out.println(s.getDataPoint(x).getTime());*/

      if (buytime.isEmpty()==false){
        Date buyDate = (Date) buytime.get(0);/*System.out.println(s.getDataPoint(x).getTime());*/

        if( testd.equals(buyDate) ){          
          if (buytime.size() ==1){
            indivOrderSize = (int) (amount - totalBought);}     
          
          TimePoint marker = new TimePoint(); 
          marker.setTime(testd);
          marker.setprice(s.getDataPoint(x).getprice());
          
          s.setPurchase(marker);
          
          System.out.println( "Purchased :"+indivOrderSize +", at "+dateFormat.format(testd)+ ", for " +s.getDataPoint(x).getprice());    
          System.out.println( " ");       

          totalCost += ( indivOrderSize*s.getDataPoint(x).getprice());         
          totalBought += indivOrderSize;

          buytime.remove(buyDate);
        }
      }
    }
    System.out.println( "Purchased a total of  :"+totalBought + " for $" +totalCost);
    System.out.println( "To aquire from a one of purchase, it would have cost : $" + (s.getDataPoint(0).getprice()*totalBought));
    
    if ( totalCost < s.getDataPoint(0).getprice()*totalBought)
      System.out.println("you saved : "+ (totalCost - s.getDataPoint(0).getprice()*totalBought) );
    else 
      System.out.println("you lost using the alg : "+ (totalCost - s.getDataPoint(0).getprice()*totalBought));
      
    return s;
  }























  public static Result VWAP() {
    Calendar start = Calendar.getInstance();
  start.set(Calendar.HOUR_OF_DAY,9);
  start.set(Calendar.MINUTE,30);
  start.set(Calendar.SECOND,0);
  start.set(Calendar.MILLISECOND,0);        
  Calendar end = Calendar.getInstance();
  end.set(Calendar.HOUR_OF_DAY,16);
  end.set(Calendar.MINUTE,00);
  end.set(Calendar.SECOND,0);
  end.set(Calendar.MILLISECOND,0);

    Stock appl = data("C:\\Users\\User\\Desktop\\Implementation\\Server\\public\\files\\Test_Data.txt");
     ArrayList buytime= buyTimes(start, end,30);
    return ok(
     VWAP.render(VWAPFunc(appl, start, end))
     );
    }



  public static Result database() {
         dbrow s = dbrow.findById( 1);
        List<dbrow> s2 = dbrow.findAll();
        ArrayList<dbrow> al = new ArrayList<dbrow>();
        for (int x = 0 ; x < s2.size() ; x++){
            al.add(s2.get(x));
        }
        System.out.println( " check table size :  "+s2.size() );
        return ok(          database.render(al )     );    }

    public static Result home() {
        return ok(boot.render() );} 
 
  public static Result menu() {
       return ok(                menu.render()            );  }    

   public static Result menu2(String s) {
       return ok(                menu.render()            );  } 

   public static Result code() {
      return ok(                code.render()            );  }
 
   public static Result fileUpload() {
      return ok(      		fileUpload.render()           );  }


public static Result upload() {

//get file from user 
  MultipartFormData body = request().body().asMultipartFormData();
  FilePart picture = body.getFile("picture");
  if (picture != null) {
    String fileName = picture.getFilename();
    String contentType = picture.getContentType(); 
    File jar = picture.getFile();
    jar.renameTo(new File("C:\\Users\\User\\Desktop\\Implementation\\Server\\lib", fileName));
     
    try { JarFile jarFile = new JarFile(jar);    

    URL url = jar.toURI().toURL();   URL[] urls = new URL[]{url};            
    ClassLoader cl = new URLClassLoader(urls);   Class classToLoadTimePoint;  Class classToLoadStock;
    classToLoadTimePoint = Class.forName ("testAlgorithm.TimePoint", true, cl);
    classToLoadStock = Class.forName ("testAlgorithm.Stock", true, cl);

    Class[] cArg = new Class[7];    cArg[0] = Date.class;  
    cArg[1] = double.class;     cArg[2] = double.class; 
    cArg[3] = double.class;   cArg[4] = double.class; 
    cArg[5] = double.class;       cArg[6] = double.class; 

     DateFormat dateFormat = new SimpleDateFormat("HH:mm");   Calendar cal = Calendar.getInstance();
     cal.set(Calendar.HOUR_OF_DAY,9);  cal.set(Calendar.MINUTE,30);
     cal.set(Calendar.SECOND,0); cal.set(Calendar.MILLISECOND,0);
                      

       Constructor<?> constructorTP = classToLoadTimePoint.getConstructor(cArg);            
       Constructor<?> constructorStock = classToLoadStock.getConstructor();                 
      Object stockObject = constructorStock.newInstance();           

    Scanner fileIn = new Scanner(new File("C:\\Users\\User\\Desktop\\Implementation\\Server\\public\\files\\Test_Data.txt"));
    fileIn.useDelimiter(",");
    Calendar cal2 = Calendar.getInstance(); cal.set(Calendar.HOUR_OF_DAY,9);cal.set(Calendar.MINUTE,30);
    cal.set(Calendar.SECOND,0); cal.set(Calendar.MILLISECOND,0);
    System.out.println(dateFormat.format(cal.getTime()));

    Stock nativeStock = new Stock(" ");

    while (fileIn.hasNextLine() == true){      
      String singleLine = fileIn.nextLine();
      String[] lineArray = singleLine.split(",");
      Float price = Float.parseFloat(lineArray[2]);   Float volume = Float.parseFloat(lineArray[5]);      
      Float high = Float.parseFloat(lineArray[2]);  Float open = Float.parseFloat(lineArray[4]);
      Float low = Float.parseFloat(lineArray[3]);   Float close = Float.parseFloat(lineArray[1]);
      Object instanceTP = constructorTP.newInstance(cal.getTime(),price,volume,high,open,low,close);      

      Method set = stockObject.getClass().getMethod("setTimeandprice", classToLoadTimePoint);
            set.invoke(stockObject, constructorTP.newInstance(cal.getTime(),price,volume,high,open,low,close));   

      Date myDate = cal.getTime();      
      TimePoint ts = new TimePoint(myDate.getTime() , cal.getTime(),price,volume,high, low,close, open);      

      //TimePoint ts = new TimePoint(cal.getTime(),price);    
      cal.add(Calendar.MINUTE, 1);      

      nativeStock.setTimeandprice(ts);    
    } 
    
    Calendar start = Calendar.getInstance();    start.set(Calendar.HOUR_OF_DAY,9);
    start.set(Calendar.MINUTE,30);    start.set(Calendar.SECOND,0);
    start.set(Calendar.MILLISECOND,0);        
    Calendar end = Calendar.getInstance();    end.set(Calendar.HOUR_OF_DAY,9);             
    end.set(Calendar.MINUTE,38);    end.set(Calendar.SECOND,0);
    end.set(Calendar.MILLISECOND,0);
            
    // Call And Run ALgorithm. 
    
    Class[] alg = new Class[4];   alg[0] = Calendar.class;alg[1] = Calendar.class;  
        alg[2] = int.class;          alg[3] = int.class;  
        
      
    Method set = stockObject.getClass().getMethod("TWAPFunc",alg);  
    System.out.println( "check is twap func "+ set.toString() );
    System.out.println( "This should be a stock " +  set.invoke( stockObject,start,end,100000,2 )) ;

    //Create Native Stock with Jar Stocks Values

     //loop over purchased array of Jar stock, place value in Native   

    Method sizeM = stockObject.getClass().getMethod("PurchaseLength");
          int size = (int)sizeM.invoke(stockObject );  

     for (int z = 0; z< size; z++){      

    Method get2 = stockObject.getClass().getMethod("getPurchasePointDateAtPoint", int.class);
          double i =   (double)get2.invoke(stockObject,z );   

          Method get = stockObject.getClass().getMethod("getPurchasePointTimeAtPoint", int.class);
          Date d =   (Date)get.invoke(stockObject,z );         

    TimePoint ts2 = new TimePoint(d, i); 
    
    nativeStock.setPurchase(ts2);       

    System.out.println( nativeStock.getDataArray().get(z).getTime());
    
    }      

     return ok(
       TWAPupload.render(nativeStock)
    );


} catch (IOException |ClassNotFoundException | NoSuchMethodException | InstantiationException |
               IllegalAccessException| InvocationTargetException e) { e.printStackTrace(); ;e.getCause(); }


}
return ok( " say it aint so . ."//uploadConfirm.render()
      );




}


public static Stock VWAPFunc(Stock s, Calendar start, Calendar end){


    DateFormat dateFormat = new SimpleDateFormat("HH:mm");      
    
    ArrayList<Date> buyTimeD = buyTimes(start,  end, 1);  
    ArrayList<Calendar> buyTimeC = buyTimesC(buyTimeD);
    
    int sharesTradedInPeriodTotal = 0;
    double pTimesVAtP = 0;
    
    for (int x = 0; x < ( (buyTimeC.size() -1 ) ) ; x++){   
      int ip = sharesTradedInPeriod(s, buyTimeC.get(x), buyTimeC.get(x+1));
      
      sharesTradedInPeriodTotal += ip;      
    
      double price = s.getDataArray().get(x).getprice();
      
      pTimesVAtP +=( ip * price);     
      
      //divied by total volume traded so far 
      double div = pTimesVAtP /sharesTradedInPeriodTotal ;      
                
      //fill up stock array     
      TimePoint marker = new TimePoint(); 
      marker.setTime(buyTimeD.get(x));
      marker.setprice(div);
      //s.getPurchaseArray().get(0).getTime()
      s.setPurchase(marker);      
    }
        
    /*for (int a = 0; a <  s.getPurchaseArray().size() ; a++){
    System.out.println("time: "+  dateFormat.format(   s.getPurchaseArray().get(a).getTime())+"   price 
    " + s.getPurchaseArray().get(a).getprice() );
    }*/
    
    return s;   
  }




  public static ArrayList<Calendar> buyTimesC(ArrayList<Date> d){
    ArrayList<Calendar> buyTimesCal = new ArrayList<Calendar>();
    for (int x = 0; x < d.size() ; x++){
      Calendar cal = Calendar.getInstance();  
      cal.setTime(d.get(x));
      buyTimesCal.add(cal);     
      }
    
    return buyTimesCal;
    
    
    }



  public static int sharesTradedInPeriod(Stock s, Calendar start,Calendar end ){
    int total = 0;
    double p1 = 0 ; double p2 = 0 ;
    Double v1 = 0.0; Double v2= 0.0; 
    Date startd  = start.getTime(); Date endd  = end.getTime();
    Date point = null;
    int mins = s.DataLength(); int flag = 0;
    for (int x = 0; x < (mins-1) ; x++){      
      point = s.getDataPoint(x).getTime();
      if (endd.equals(s.getDataPoint(x).getTime()))
        flag = 0; 
      if (startd.equals(point))
        flag = 1;
      if (flag == 1){
        p1 = s.getDataPoint(x).getvolume(); p2 = s.getDataPoint(x+1).getvolume();       
        double ddiff = p2- p1; int dif = (int)ddiff;
        dif =  Math.abs(dif);
        total += dif ;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Date first = s.getDataPoint(x).getTime();
        Date second = s.getDataPoint(x+1).getTime();

        v1 =s.getDataPoint(x).getvolume();
        v2 =s.getDataPoint(x+1).getvolume();
        
//        System.out.println("  ");
//        System.out.println("Time " +dateFormat.format(first)+ " ,Volume "+ v1); 
//        System.out.println("Time " +dateFormat.format(second)+ " ,Volume "+ v2);  
//        System.out.println("Difference : "+ dif);
        } 
//      System.out.println("Total = " +v1+ " + "+v2+" = "+ total);    

            
      }
      
//    System.out.println("Total = " + total);   
    return total;
  }





public static ArrayList<Date> buyTimes(Calendar s, Calendar e, int period){
    ArrayList<Date> buyTimes = new ArrayList<Date>();
    double diff = e.getTimeInMillis() - s.getTimeInMillis();
    double numOfMinutes=((diff)/1000)/60;
    double numOfOrders = numOfMinutes / period;
    Date startd = s.getTime() ;  
    buyTimes.add(startd);
    Calendar cal = Calendar.getInstance();  
    cal.setTime(startd);
    for (int x = 0; x < numOfOrders ; x++){
      cal.add(Calendar.MINUTE,period);
      Date d4 = cal.getTime();
      buyTimes.add(d4);   }
//    for (int x = 0; x < numOfOrders ; x++){
//      System.out.println(buyTimes.get(x));      
//    }
    return buyTimes;    
  }



  /*([
[1317888000000,372.5101,375,372.2,372.52],[1318607940000,421.94,422,421.8241,422]
]);*/

  public static Result data() {
      return ok("([[1317888000000,372.5101,375,372.2,372.52],[1318607940000,421.94,422,421.8241,422]]); "  );
        } 
 







 /**
   * Returns the page where the form is filled by the Student whose id is passed, or an empty form
   * if the id is 0.
   * @param id The id of the Student whose data is to be shown.  0 if an empty form is to be shown.
   * @return The page containing the form and data.
   */
  public static Result getIndex(long id) {
    StudentFormData studentData = (id == 0) ? new StudentFormData() : models.Student.makeStudentFormData(id);
    Form<StudentFormData> formData = Form.form(StudentFormData.class).fill(studentData);
    return ok(index.render(
      formData,
      Hobby.makeHobbyMap(studentData),
      GradeLevel.getNameList(),
      GradePointAverage.makeGPAMap(studentData),
      Major.makeMajorMap(studentData)
    ));
  }



  /**
   * Process a form submission.
   * First we bind the HTTP POST data to an instance of StudentFormData.
   * The binding process will invoke the StudentFormData.validate() method.
   * If errors are found, re-render the page, displaying the error data. 
   * If errors not found, render the page with the good data. 
   * @return The index page with the results of validation. 
   */
  public static Result postIndex() {

    // Get the submitted form data from the request object, and run validation.
    Form<StudentFormData> formData = Form.form(StudentFormData.class).bindFromRequest();

    if (formData.hasErrors()) {
      // Don't call formData.get() when there are errors, pass 'null' to helpers instead. 
      flash("error", "Please correct errors above.");
      return badRequest(index.render(formData,
        Hobby.makeHobbyMap(null), 
        GradeLevel.getNameList(),
        GradePointAverage.makeGPAMap(null), 
        Major.makeMajorMap(null) 
      ));
    }
    else {
      // Convert the formData into a Student model instance.
      Student student = Student.makeInstance(formData.get());
      flash("success", "Student instance created/edited: " + student);
      return ok(index.render(formData,
        Hobby.makeHobbyMap(formData.get()),
        GradeLevel.getNameList(),
        GradePointAverage.makeGPAMap(formData.get()),
        Major.makeMajorMap(formData.get())
      ));
    }
  }

/*// EXAMPLE BELOW WORKS WITH MYJAR>JAR ON DESKTOP TO DISPLAY CONSOLE OUTPUT 



//public static Result upload() {

  //get file from user 
  MultipartFormData body = request().body().asMultipartFormData();
  FilePart picture = body.getFile("picture");
  if (picture != null) {
    String fileName = picture.getFilename();
    String contentType = picture.getContentType(); 
    File jar = picture.getFile();
    jar.renameTo(new File("C:\\Users\\JG\\Desktop\\Att\\lib", fileName));


    try {URLClassLoader classLoader = new URLClassLoader( new URL[]{jar.toURL()} ); 

      
    try {JarFile jarFile = new JarFile(jar);    
    
      //find main declaration in .jar by reading the manifest 
      Attributes attribs = jarFile.getManifest().getMainAttributes();
      String mainClass = attribs.getValue("Main-Class");

      
    try {Class<?> clazz = classLoader.loadClass(mainClass);
      
    
                //return method called "main" 
    try {       Method main = clazz.getMethod("main", String[].class);
    
     
    try {        main.invoke(null, new Object[]{new String[]{"arg0", "arg1"}});





    } catch (IllegalAccessException e) { e.printStackTrace();
    } catch (IllegalArgumentException e) {e.printStackTrace();
    } catch (InvocationTargetException e) {e.printStackTrace(); }     
      
    } catch (SecurityException e) {e.printStackTrace();   }      
    } catch (NoSuchMethodException e) {e.printStackTrace();      
    } catch (ClassNotFoundException e) {e.printStackTrace();}      
    } catch (IOException e) {e.printStackTrace();}      
    } catch (MalformedURLException e) { e.printStackTrace();}



    return ok( uploadConfirm.render()
      );
    /*return ok("File uploaded");
 // } else {
   // flash("error", "Missing file");
   //  return ok("File Not uploaded");
    /*return redirect(routes.Application.index());*/    
//  }*/




//}*/










}
