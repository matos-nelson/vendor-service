insert into vendor(id, owner_id, address_id, name, email, phone) values (100, 200, 300, 'Update Vendor', 'updatevendortest@email.com', '1231234444');

insert into vendor(id, owner_id, address_id, name, email, phone) values (400, 500, 600, 'First Vendor', 'vendor@email.com', '1234567890');
insert into vendor(id, owner_id, address_id, name, email, phone) values (401, 500, 800, 'Second Vendor', 'second_vendor@email.com', '1231231234');
insert into worker(id, vendor_id , name, email, phone, is_active) values (700, 400, 'First Worker', 'worker@email.com', '3216540987', true);