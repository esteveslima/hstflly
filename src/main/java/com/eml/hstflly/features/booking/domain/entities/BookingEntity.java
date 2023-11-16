package com.eml.hstflly.features.booking.domain.entities;

import com.eml.hstflly.features.property.domain.entities.PropertyEntity;
import com.eml.hstflly.features.user.domain.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private int id;

    @ManyToOne()
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private PropertyEntity property;

    @Column(name = "guestsNumber")
    private Integer guestsNumber;

    @Column(name = "arrivalHour")
    private Integer arrivalHour;

    @Column(name = "checkinDate")
    @Temporal(TemporalType.DATE)
    private Date checkinDate;

    @Column(name = "checkoutDate")
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public BookingEntity.BookingType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public BookingEntity.BookingStatus status;

    @Version
    private Long version;

    //

    public static enum BookingType {
        BOOKING,
        BLOCK;

        public class MapValue {
            public static final String BOOKING = "BOOKING";
            public static final String BLOCK = "BLOCK";
        }
    }

    public static enum BookingStatus {
        ENABLED,
        CANCELLED;


        public class MapValue {
            public static final String ENABLED = "ENABLED";
            public static final String CANCELLED = "CANCELLED";
        }
    }
}
