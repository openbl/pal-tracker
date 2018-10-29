package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.TimeEntry;
import io.pivotal.pal.tracker.TimeEntryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private long nextTimeEntryId = 1L;
    private Map<Long, TimeEntry> store = new HashMap<>();

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(nextTimeEntryId);
        store.put(nextTimeEntryId, timeEntry);
        nextTimeEntryId += 1;
        return timeEntry;
    }

    public TimeEntry find(long timeEntryId) {
        return store.get(timeEntryId);
    }

    public List<TimeEntry> list() {
        return store.values().stream().collect(Collectors.toList());
    }

    public TimeEntry update(long timeEntryId, TimeEntry timeEntry) {
        final TimeEntry old = store.get(timeEntryId);
        timeEntry.setId(old.getId());
        store.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public void delete(long timeEntryId) {
        store.remove(timeEntryId);
    }
}
