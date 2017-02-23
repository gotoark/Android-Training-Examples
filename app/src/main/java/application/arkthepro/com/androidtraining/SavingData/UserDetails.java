package application.arkthepro.com.androidtraining.SavingData;

/**
 * Created by RAJESH KUMAR ARUMUGAM on 20-02-2017.
 */
public class UserDetails {
    String name;
    String phonenumber;
    int id;
    byte[] profilepic;
    //Empty Constructor
    public  UserDetails(){

    }
    public UserDetails(int id,String name,String phonenumber,byte[] profilepic){
        this.name=name;
        this.phonenumber=phonenumber;
        this.id=id;
        this.profilepic=profilepic;
    }
    public  UserDetails(String name,String phonenumber,byte[] profilepic){
       this.name=name;
        this.phonenumber=phonenumber;
        this.profilepic=profilepic;
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
    //Set Id
    public void setId(int id){
        this.id=id;
    }

     //Get Profile Pic
    public byte[] getprofilepic(){
        return this.profilepic;
    }
    //Set Pic
    public void setProfilepic(byte[] profilepic){
       this.profilepic=profilepic;
    }

    
    
}
