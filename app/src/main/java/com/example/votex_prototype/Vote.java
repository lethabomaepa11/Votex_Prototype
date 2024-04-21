package com.example.votex_prototype;

public class Vote
 {
     private String voterId;
     private String candidateId;
     private String portfolio;
     
     
     public Vote(){};
     
     public Vote(String voterId,String candidateId,String portfolio)
     {
         this.voterId = voterId;
         this.candidateId = candidateId;
         this.portfolio = portfolio;
     }
     
 }