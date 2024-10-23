package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByUsername(username);
        List<JournalEntry> all= user.getJournalEntries();
        if(all!=null && !all.isEmpty())
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> addJournalEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username=authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter( x-> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry = journalEntryService.findEntryById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<JournalEntry>(journalEntry.get(),HttpStatus.OK);
            }

        }

            return  new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId myId) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        boolean removed = journalEntryService.deleteEntry(myId,username);
        if(removed) {
            return  new ResponseEntity<JournalEntry>(HttpStatus.NO_CONTENT);
        }
        else
        return  new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry myEntry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter( x-> x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry = journalEntryService.findEntryById(myId);
            if(journalEntry.isPresent()){
                JournalEntry old = journalEntry.get();
                old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent() );
                old.setTitle(myEntry.getTitle()!=null && !myEntry.getContent().equals("") ? myEntry.getTitle() : old.getTitle());
                journalEntryService.saveEntry(old);
                return  new ResponseEntity<>(old,HttpStatus.OK);
            }

        }

            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

}
