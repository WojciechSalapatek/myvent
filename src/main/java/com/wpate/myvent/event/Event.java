package com.wpate.myvent.event;

import com.wpate.myvent.category.Category;
import com.wpate.myvent.user.ApplicationUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser owner;
    private String shortDescription;
    private String fullDescription;
    private String address;
    private int totalFields;
    private int freeFields;


    public static final class EventBuilder {
        private long id;
        private String name;
        private Date date;
        private Category category;
        private ApplicationUser owner;
        private String shortDescription;
        private String fullDescription;
        private String address;
        private int totalFields;
        private int freeFields;

        private EventBuilder(long id, String name, Date date) {
            this.id = id;
            this.name = name;
            this.date = date;
        }

        public static EventBuilder anEvent(long id, String name, Date date) {
            return new EventBuilder(id, name, date);
        }

        public EventBuilder withId(long id) {
            this.id = id;
            return this;
        }

        public EventBuilder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public EventBuilder withOwner(ApplicationUser owner) {
            this.owner = owner;
            return this;
        }

        public EventBuilder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public EventBuilder withFullDescription(String fullDescription) {
            this.fullDescription = fullDescription;
            return this;
        }

        public EventBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public EventBuilder withTotalFields(int totalFields) {
            this.totalFields = totalFields;
            return this;
        }

        public EventBuilder withFreeFields(int freeFields) {
            this.freeFields = freeFields;
            return this;
        }

        public Event build() {
            Event event = new Event();
            event.setId(id);
            event.setName(name);
            event.setDate(date);
            event.setCategory(category);
            event.setOwner(owner);
            event.setShortDescription(shortDescription);
            event.setFullDescription(fullDescription);
            event.setAddress(address);
            event.setTotalFields(totalFields);
            event.setFreeFields(freeFields);
            return event;
        }
    }
}
