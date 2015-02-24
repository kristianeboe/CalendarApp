-- Check if room is reserved at given time interval. Returns the room if is booked. Returns nothing if room is available

SELECT * FROM room NATURAL JOIN appointment WHERE room.roomID='roomID' AND (('fromDateTime' > from_time AND 'fromDateTime' < to_time) OR ('toDateTime'> from_time AND 'toDateTime'<to_time) OR ('fromDateTime' < from_time AND 'toDateTime' > to_time));

-- SELECT smallest room with large enough capacity that is not reserved at given time interval

SELECT * FROM room WHERE capacity >='num_people' AND roomID NOT IN( SELECT roomID FROM appointment WHERE ( ('fromDateTime' > from_time AND 'fromDateTime' < to_time) OR ('toDateTime'> from_time AND 'toDateTime'<to_time) OR ('fromDateTime' < from_time AND 'toDateTime' > to_time) ) ) ORDER BY capacity ASC LIMIT 1;

-- Book room (Remember to check if room is available)

UPDATE appointment SET roomID = 'roomID';

-- Delete reservation

UPDATE appointment SET roomID = NULL WHERE appointmentID = 'appointmentID';

-- Change reservation time = change appointment time (Remember to check if room is available for the new time)

UPDATE appointment SET from_time = 'NewFromDateTime', to_time = 'NewToDateTime' WHERE 'appointmentID' = appointmentID;