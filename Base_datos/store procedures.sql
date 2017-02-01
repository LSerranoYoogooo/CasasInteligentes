//USUARIOS

//SET USER
DELIMITER $$ 
CREATE PROCEDURE set_User( IN name VARCHAR( 160 ), IN email VARCHAR( 160 ),  IN pass VARCHAR( 160 ), IN service VARCHAR( 10 ) )
BEGIN
INSERT INTO user_app(usr_name, usr_email, usr_pass, usr_service_type) VALUES (name, email, pass, service);
END


//SITIOS
DELIMITER $$ 
CREATE PROCEDURE set_Site( IN usr_id VARCHAR( 160 ), IN name VARCHAR( 160 ), IN latitud VARCHAR( 160 ),  IN longitud VARCHAR( 160 ), IN img VARCHAR( 10 ), IN ip_address VARCHAR( 160 ), IN ip_port VARCHAR( 160 ) )
BEGIN
DECLARE ID INT;
INSERT INTO site(usr_id, st_name, st_latitud, st_longitud, st_img) VALUES (usr_id, name, latitud, longitud, img);
SET ID = LAST_INSERT_ID();
INSERT INTO ip_address(st_id, usr_id, ipa_ip, ipa_port) VALUES (ID, usr_id, ip_address, ip_port);
END


//IP_address


//Enclouser
DELIMITER $$ 
CREATE PROCEDURE set_Enclouser( IN site_id VARCHAR( 160 ), IN name VARCHAR( 160 ),  IN img VARCHAR( 160 ) )
BEGIN
INSERT INTO enclouser(st_id, enc_name, enc_img) VALUES (site_id, name, img);
END


//Control
DELIMITER $$ 
CREATE PROCEDURE set_Control( IN enc_id VARCHAR( 160 ), IN name VARCHAR( 160 ),  IN voice_on VARCHAR( 160 ),  IN voice_off VARCHAR( 160 ),  IN channel VARCHAR( 160 ),  IN img VARCHAR( 160 ) )
BEGIN
INSERT INTO control(enc_id, ctrl_name, ctrl_voice_on, ctrl_voice_off, ctrl_channel, ctrl_img)  VALUES (enc_id, name, voice_on, voice_off, channel, img);
END
