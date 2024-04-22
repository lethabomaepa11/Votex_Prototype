package com.example.votex_prototype;

public class Vote
 {
     private String voterId;
     private String candidateId;
     private String portfolio;
     private String voteId;
     
     
     public Vote(){};
     
     public Vote(String voterId,String candidateId)
     {
         this.voterId = voterId;
         this.candidateId = candidateId;
     }

     public String getPortfolio() {
         return portfolio;
     }

     public String getCandidateId() {
         return candidateId;
     }

     public String getVoteId() {
         this.voteId = "V"+this.candidateId+this.voteId+Math.random();
         return voteId;
     }

     public String getVoterId() {
         return voterId;
     }
 }