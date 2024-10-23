package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String username) {

        try {
            User user=userService.findByUsername(username);
            JournalEntry saved=journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }
        catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteEntry(ObjectId id,String username) {
        boolean removed = false;
        try {
            User user=userService.findByUsername(username);
            removed=user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error in deleting user :",e);
        }

        return removed;

    }
}
