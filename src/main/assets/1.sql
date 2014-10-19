DROP TABLE IF EXISTS account;
CREATE TABLE account (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  currency TEXT NOT NULL,
  amount REAL NOT NULL DEFAULT '0'
);
INSERT INTO account VALUES (1,'Wallet','€',56.48),(2,'Desk','€',602.5),(3,'Bank','$',5954.32);

DROP TABLE IF EXISTS debt;
CREATE TABLE debt (
  _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  account_id INTEGER DEFAULT NULL,
  tag_id INTEGER DEFAULT NULL,
  name TEXT NOT NULL,
  description TEXT DEFAULT NULL,
  amount REAL NOT NULL DEFAULT '0',
  FOREIGN KEY (account_id) REFERENCES account (_id),
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
  color INTEGER NOT NULL,
  name TEXT NOT NULL
);
INSERT INTO tag VALUES (1,-65536,'Drinks'),(2,-16711936,'Food'),(3,-16776961,'Stuff');