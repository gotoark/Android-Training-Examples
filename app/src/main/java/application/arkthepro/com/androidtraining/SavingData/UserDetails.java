package application.arkthepro.com.androidtraining.SavingData;

/**
 * Created by i00589 on 20-02-2017.
 */
public class UserDetails {
    String name;
    String phonenumber;
    int id;
    //Empty Constructor
    public  UserDetails(){

    }
    public UserDetails(int id,String name,String phonenumber){
        this.name=name;
        this.phonenumber=phonenumber;
        this.id=id;
    }
    public  UserDetails(String name,String phonenumber){
       this.name=name;
        this.phonenumber=phonenumber;
    }
    //GetName
    public String getName(){
        return this.name;
    }

    //SetName
public void setName(String name){
    this.name=name;
}
    //GetPhoneNumber
    public String getphoneNumber(){
        return this.phonenumber;
    }

    //SetPhoneNumber
    public void setPhoneNumber(String phonenumber){
        this.phonenumber=phonenumber;
    }

    //GetId
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
    }

    
    
}
