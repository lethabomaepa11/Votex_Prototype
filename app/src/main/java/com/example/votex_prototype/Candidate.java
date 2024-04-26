package com.example.votex_prototype;
public class Candidate
 {
     private String name;
     private String portfolio;
     private String id;
     private String party;
     
     
     public Candidate(){};
     
     public Candidate(String name,String portfolio,String id,String party)
     {
         this.name = name;
         this.portfolio = portfolio;
         this.id = id;
         this.party = party;
     }

     public String getParty() {
         return this.party;
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