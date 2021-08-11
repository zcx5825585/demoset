package org.zcx.feign_client.pojo;

public class BookInfo {
    private String seat;
    private String startTime = "0";
    private String endTime = "1440";
    private String dateString;
    private Boolean isNextDay;
    public static String ANY_SEAT = "any";

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Boolean getNextDay() {
        return isNextDay;
    }

    public void setNextDay(Boolean nextDay) {
        isNextDay = nextDay;
    }

    public BookInfo getQuery() {
        BookInfo query = new BookInfo();
        query.setSeat(this.seat);
        query.setStartTime(this.startTime);
        query.setEndTime(this.endTime);
        return query;
    }
}
