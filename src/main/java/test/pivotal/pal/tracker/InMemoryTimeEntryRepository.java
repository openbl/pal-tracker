package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private long nextTimeEntryId = 1L;
    private Map<Long, TimeEntry> store = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry) {
        final TimeEntry newTimeEntry = new TimeEntry(
                nextTimeEntryId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        store.put(nextTimeEntryId, newTimeEntry);
        nextTimeEntryId += 1;

        return newTimeEntry;
    }

    public TimeEntry find(long timeEntryId) {
        return store.get(timeEntryId);
    }

    public List<TimeEntry> list() {
        return new ArrayList<>(store.values());
    }

    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        final TimeEntry updatedTimeEntry = new TimeEntry(
                timeEntryId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        store.put(timeEntryId, updatedTimeEntry);

        return updatedTimeEntry;
    }

    public void delete(long timeEntryId) {
        store.remove(timeEntryId);
    }
}
