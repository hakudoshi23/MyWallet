DROP TABLE IF EXISTS account;
CREATE TABLE account (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  amount REAL NOT NULL DEFAULT '0'
);
INSERT INTO account VALUES (1,'Wallet',56.48),(2,'Desk',602.5),(3,'Bank',5954.32);

DROP TABLE IF EXISTS color;
CREATE TABLE color (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  value INTEGER NOT NULL
);
INSERT INTO color VALUES (1,'White',-1),(2,'Black',-16777216),(3,'Red',-65536),(4,'Blue', -16776961),(5,'Green',-16711936),(6,'Cyan',-16776961);

DROP TABLE IF EXISTS cycle;
CREATE TABLE cycle (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  tag_id INTEGER DEFAULT NULL,
  name TEXT NOT NULL,
  description TEXT DEFAULT NULL,
  amount REAL NOT NULL DEFAULT '0',
  period TEXT NOT NULL,
  FOREIGN KEY (tag_id) REFERENCES tag (_id)
);
INSERT INTO cycle VALUES (1,3,'MasMobil','Phone contract',-6.05,'MONTHLY'),(2,3,'T-Jove','Train card for the next 3 months',-105,'MONTHLY');

DROP TABLE IF EXISTS debt;
CREATE TABLE debt (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  cycle_id INTEGER DEFAULT NULL,
  tag_id INTEGER DEFAULT NULL,
  name TEXT NOT NULL,
  description TEXT DEFAULT NULL,
  amount REAL NOT NULL DEFAULT '0',
  FOREIGN KEY (cycle_id) REFERENCES cycle (_id),
  FOREIGN KEY (tag_id) REFERENCES tag (_id)
);
INSERT INTO debt VALUES (1,1,3,'MasMobil','Phone contract',-6.05),(2,2,3,'T-Jove','Train card for the next 3 months',-105);

DROP TABLE IF EXISTS move;
CREATE TABLE move (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  account_id INTEGER NOT NULL,
  debt_id INTEGER DEFAULT NULL,
  tag_id INTEGER DEFAULT NULL,
  name TEXT NOT NULL,
  description TEXT DEFAULT NULL,
  amount REAL NOT NULL DEFAULT '0',
  FOREIGN KEY (account_id) REFERENCES account (_id),
  FOREIGN KEY (debt_id) REFERENCES debt (_id),
  FOREIGN KEY (tag_id) REFERENCES tag (_id)
);
INSERT INTO move VALUES (1,1,NULL,1,'Cocktail','Party hard!',-5.5),(2,3,1,3,'MasMobil','Phone contract',-6.05),(3,1,NULL,2,'Because','Weekend junk food',12.64),(4,2,2,3,'T-Jove','Train card for the next 3 months',-105);

DROP TABLE IF EXISTS tag;
CREATE TABLE tag (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  color_id INTEGER NOT NULL,
  name TEXT NOT NULL,
  FOREIGN KEY (color_id) REFERENCES color (_id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
INSERT INTO tag VALUES (1,1,'Drinks'),(2,2,'Food'),(3,3,'Stuff');
