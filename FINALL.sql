-- Generado por Oracle SQL Developer Data Modeler 4.1.3.901
--   en:        2016-11-20 14:07:04 COT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g

--Elimiar tablas

DROP TABLE Contactos;
DROP TABLE miembroxtransaccion;
DROP TABLE miembroxfactura;
DROP TABLE Historial;
DROP TABLE miembro;
DROP TABLE transaccion;
DROP TABLE factura;
DROP TABLE Rol;
DROP TABLE Usuario;
DROP TABLE grupo;





CREATE TABLE Factura
  (
    Factura_Id NUMBER (5) NOT NULL ,
    Nombre NVARCHAR2 (30) NOT NULL ,
    Costo       NUMBER (10) NOT NULL ,
    Comprobante NUMBER (2) ,
    Fecha       DATE NOT NULL ,
    Hora        VARCHAR2 (10) NOT NULL ,
    Ubicacion NVARCHAR2 (20) ,
    Creador_miembro NVARCHAR2 (1) NOT NULL ,
    comentarios NVARCHAR2 (100) ,
    imagen BLOB ,
    monto NUMBER (30)
  ) ;
ALTER TABLE Factura ADD CONSTRAINT Factura_PK PRIMARY KEY ( Factura_Id ) ;


CREATE TABLE Grupo
  (
    Grupo_Id NUMBER (3) NOT NULL ,
    Nombre NVARCHAR2 (30) ,
    Estado NVARCHAR2 (30)
  ) ;
ALTER TABLE Grupo ADD CONSTRAINT Grupo_PK PRIMARY KEY ( Grupo_Id ) ;


CREATE TABLE Historial
  (
    fecha              DATE NOT NULL ,
    Miembro_Miembro_Id NUMBER (3) NOT NULL ,
    Grupo_Grupo_Id     NUMBER (3) NOT NULL ,
    Rol_Rol_id         NUMBER (2) NOT NULL
  ) ;
ALTER TABLE Historial ADD CONSTRAINT Historial_PK PRIMARY KEY ( Rol_Rol_id, fecha, Miembro_Miembro_Id, Grupo_Grupo_Id ) ;


CREATE TABLE Miembro
  (
    Estado            NUMBER (2) NOT NULL ,
    Usuario_Nick_name NVARCHAR2 (50) NOT NULL ,
    Miembro_Id        NUMBER (3) NOT NULL ,
    monto             NUMBER (30) ,
    Tipo              NUMBER (2) ,
    Monto1            NUMBER (10) ,
    Afectado_Id       NUMBER (3)
  ) ;
ALTER TABLE Miembro ADD CONSTRAINT Miembro_PK PRIMARY KEY ( Miembro_Id ) ;


CREATE TABLE MiembroXFactura
  (
    Miembro_Miembro_Id NUMBER (3) NOT NULL ,
    Factura_Factura_Id NUMBER (5) NOT NULL ,
    monto              NUMBER (30)
  ) ;
ALTER TABLE MiembroXFactura ADD CONSTRAINT MiembroXFactura_PK PRIMARY KEY ( Miembro_Miembro_Id, Factura_Factura_Id ) ;


CREATE TABLE MiembroXTransaccion
  (
    Miembro_Miembro_Id         NUMBER (3) NOT NULL ,
    Transaccion_Transaccion_Id NUMBER (5) NOT NULL ,
    Tipo                       NUMBER (2) NOT NULL ,
    Monto                      NUMBER (10) NOT NULL ,
    Afectado_Id                NUMBER (3) NOT NULL
  ) ;
ALTER TABLE MiembroXTransaccion ADD CONSTRAINT MiembroXTransaccion_PK PRIMARY KEY ( Miembro_Miembro_Id, Transaccion_Transaccion_Id ) ;


CREATE TABLE Rol
  (
    Rol_id NUMBER (2) NOT NULL ,
    Nombre VARCHAR2 (15) NOT NULL
  ) ;
ALTER TABLE Rol ADD CONSTRAINT Rol_PK PRIMARY KEY ( Rol_id ) ;


CREATE TABLE Transaccion
  (
    Transaccion_Id NUMBER (5) NOT NULL ,
    Fecha          DATE NOT NULL ,
    Hora           DATE NOT NULL ,
    Description NVARCHAR2 (30) ,
    aprobado    NUMBER (2) NOT NULL ,
    Tipo        NUMBER (2) NOT NULL ,
    Monto       NUMBER (10) NOT NULL ,
    Afectado_Id NUMBER (3) NOT NULL
  ) ;
ALTER TABLE Transaccion ADD CONSTRAINT Transaccion_PK PRIMARY KEY ( Transaccion_Id ) ;


CREATE TABLE Usuario
  (
    Nick_name NVARCHAR2 (50) NOT NULL ,
    Nombre NVARCHAR2 (30) NOT NULL ,
    Telefono NVARCHAR2 (15) ,
    Email NVARCHAR2 (20) ,
    Usuario_PayPal NVARCHAR2 (20) ,
    clave NVARCHAR2 (20) NOT NULL ,
    clave1 NVARCHAR2 (20) 
  ) ;
ALTER TABLE Usuario ADD CONSTRAINT Usuario_PK PRIMARY KEY ( Nick_name ) ;


CREATE TABLE contactos
  (
    Usuario_Nick_name NVARCHAR2 (50) NOT NULL ,
    Usuario_Nick_name1 NVARCHAR2 (50) NOT NULL
  ) ;
ALTER TABLE contactos ADD CONSTRAINT contactos_PK PRIMARY KEY ( Usuario_Nick_name, Usuario_Nick_name1 ) ;


ALTER TABLE MiembroXFactura ADD CONSTRAINT FK_ASS_1 FOREIGN KEY ( Miembro_Miembro_Id ) REFERENCES Miembro ( Miembro_Id ) ;

ALTER TABLE contactos ADD CONSTRAINT FK_ASS_10 FOREIGN KEY ( Usuario_Nick_name ) REFERENCES Usuario ( Nick_name ) ;

ALTER TABLE contactos ADD CONSTRAINT FK_ASS_11 FOREIGN KEY ( Usuario_Nick_name1 ) REFERENCES Usuario ( Nick_name ) ;

ALTER TABLE MiembroXFactura ADD CONSTRAINT FK_ASS_2 FOREIGN KEY ( Factura_Factura_Id ) REFERENCES Factura ( Factura_Id ) ;

ALTER TABLE MiembroXTransaccion ADD CONSTRAINT FK_ASS_5 FOREIGN KEY ( Miembro_Miembro_Id ) REFERENCES Miembro ( Miembro_Id ) ;

ALTER TABLE MiembroXTransaccion ADD CONSTRAINT FK_ASS_6 FOREIGN KEY ( Transaccion_Transaccion_Id ) REFERENCES Transaccion ( Transaccion_Id ) ;

ALTER TABLE Historial ADD CONSTRAINT Historial_Grupo_FK FOREIGN KEY ( Grupo_Grupo_Id ) REFERENCES Grupo ( Grupo_Id ) ;

ALTER TABLE Historial ADD CONSTRAINT Historial_Miembro_FK FOREIGN KEY ( Miembro_Miembro_Id ) REFERENCES Miembro ( Miembro_Id ) ;

ALTER TABLE Historial ADD CONSTRAINT Historial_Rol_FK FOREIGN KEY ( Rol_Rol_id ) REFERENCES Rol ( Rol_id ) ;

ALTER TABLE Miembro ADD CONSTRAINT Miembro_Usuario_FK FOREIGN KEY ( Usuario_Nick_name ) REFERENCES Usuario ( Nick_name ) ;




