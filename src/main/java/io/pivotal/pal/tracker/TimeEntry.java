package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
    public long id;
    public long projectId;
    public long userId;
    public LocalDate date;
    public int hours;

    public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry() {

    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (!(o instanceof TimeEntry)) return false;

        final TimeEntry other = (TimeEntry) o;
        return projectId == other.projectId
                && userId == other.userId
                && date.equals(other.date)
                && hours == other.hours;
    }

    public String toString() {
        return "TimeEntry(projectId=" + projectId +
                ", userId=" + userId +
                ", date=" + date +
                ", hours=" + hours +
                ")";

    }
}
