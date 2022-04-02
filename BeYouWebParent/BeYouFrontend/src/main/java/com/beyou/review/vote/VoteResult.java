package com.beyou.review.vote;

public class VoteResult {
    private String message;
    private  boolean successfull;
    private int voteCount;

    public static VoteResult fail(String message){
        return new VoteResult(false, message, 0);
    }

    public static VoteResult success(String message,int voteCount){
        return new VoteResult(true, message, voteCount);
    }
    
    private VoteResult(boolean successfull,String message,int voteCount) {
        this.successfull = successfull;
        this.message = message;
        this.voteCount = voteCount;
    }


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccessfull() {
        return successfull;
    }
    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }
    public int getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    
}
