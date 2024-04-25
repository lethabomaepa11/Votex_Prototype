package com.example.votex_prototype;
public class User
 {
     public String name;
     public String email;
     public String id;
     public String gender;
     public String password;
     private boolean hasVoted;
     
     
     public User(){};
     
     public User(String id,String name,String email,String password,String gender)
     {
         this.name = name;
         this.email = email;
         this.id = id;
         this.gender = gender;
         this.password = password;
     }
     public void setHasVoted()
     {
         this.hasVoted = true;
     }
     public boolean getHasVoted()
     {
         return this.hasVoted;
     }
     
    
 }
 
 