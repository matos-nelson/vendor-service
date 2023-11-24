insert into vendor(id, manager_id, address_id, name, email, phone) values (100, '200', 300, 'Update Vendor', 'updatevendortest@email.com', '1231234444');

insert into vendor(id, manager_id, address_id, name, email, phone) values (400, '500', 600, 'First Vendor', 'vendor@email.com', '1234567890');
insert into worker(id, vendor_id , name, email, phone, is_active) values (700, 400, 'First Worker', 'worker@email.com', '3216540987', true);
insert into worker(id, vendor_id , name, email, phone, is_active) values (800, 400, 'UnActive Worker', 'unactiveworker@email.com', '5896734599', false);