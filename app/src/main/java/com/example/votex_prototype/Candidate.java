package com.example.votex_prototype;
public class Candidate
 {
     private String name;
     private String portfolio;
     private String id;
     
     
     public Candidate(){};
     
     public Candidate(String name,String portfolio,String id)
     {
         this.name = name;
         this.portfolio = portfolio;
         this.id = id;
     }

     public String getId() {
         return id;
     }

     public String getName() {
         return name;
     }

     public String getPortfolio() {
         return portfolio;
     }
 }