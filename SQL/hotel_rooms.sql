UPDATE hotel.rooms SET type = 'simgle', status = 'reserved', booked = 1, user = '', reservation_id = 0 WHERE room_no = 101;
UPDATE hotel.rooms SET type = 'double', status = 'vacancy', booked = 0, user = '', reservation_id = 0 WHERE room_no = 102;
UPDATE hotel.rooms SET type = 'single', status = 'in use', booked = 0, user = '', reservation_id = 0 WHERE room_no = 103;