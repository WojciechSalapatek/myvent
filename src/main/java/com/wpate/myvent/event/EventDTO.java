package com.wpate.myvent.event;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class EventDTO {

    private long id;
    @Length(min = 3, max = 30)
    private String name;
    private Date date;
    private long categoryId;
    private long ownerId;
    private String shortDescription;
    private String fullDescription;
    private String address;
    private int totalFields;
    private int freeFields;


    public static final class EventDTOBuilder {
        private long id;
        private String name;
        private Date date;
        private long categoryId;
        private long ownerId;
        private String shortDescription;
        private String fullDescription;
        private String address;
        private int totalFields;
        private int freeFields;

        private EventDTOBuilder(long id, String name, Date date) {
            this.id = id;
            this.name = name;
            this.date = date;
        }

        public static EventDTOBuilder anEventDTO(long id, String name, Date date) {
            return new EventDTOBuilder(id, name, date);
        }


        public EventDTOBuilder withCategoryName(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public EventDTOBuilder withOwnerName(long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public EventDTOBuilder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public EventDTOBuilder withFullDescription(String fullDescription) {
            this.fullDescription = fullDescription;
            return this;
        }

        public EventDTOBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public EventDTOBuilder withTotalFields(int totalFields) {
            this.totalFields = totalFields;
            return this;
        }

        public EventDTOBuilder withFreeFields(int freeFields) {
            this.freeFields = freeFields;
            return this;
        }

        public EventDTO build() {
            EventDTO eventDTO = new EventDTO();
            eventDTO.shortDescription = this.shortDescription;
            eventDTO.address = this.address;
            eventDTO.date = this.date;
            eventDTO.freeFields = this.freeFields;
            eventDTO.categoryId = this.categoryId;
            eventDTO.totalFields = this.totalFields;
            eventDTO.fullDescription = this.fullDescription;
            eventDTO.id = this.id;
            eventDTO.ownerId = this.ownerId;
            eventDTO.name = this.name;
            return eventDTO;
        }
    }
}
