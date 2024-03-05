const path = require('path')
const dotenv = require('dotenv').config({ path: path.resolve("services", '../.env') }).parsed;
const JWT_SECRET = dotenv.JWT_SECRET; const JWT_EXPIRES_IN = dotenv.JWT_EXPIRES_IN;
const db = require('../db');

/* dotenv does not want to load values despite storing 
  them in an object called "parsed" so I have to get 
  them from parsed for it to work */
const helper = require('../helper');
const config = require('../config');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { AxiosHeaders } = require('axios');

async function getMultiple(){
  const rows = await db.query(
    `SELECT * FROM utilisateurs`,
    []  );
  const data = helper.emptyOrRows(rows);

  return {
    data
  }
}

async function getOne(id){
  const rows = await db.query(
    `SELECT * FROM utilisateurs WHERE pk_idAdmin=?`,
    [id]  );
  const data = helper.emptyOrRows(rows);

  return {
    data
  }
}

async function create(utilisateur){
  const result = await db.query(
    `INSERT INTO utilisateurs (nom_utilisater, mot_de_passe) VALUES (?, ?)`,
    [utilisateur.nom_utilisateur, utilisateur.mot_de_passe]  );

  let message = 'Error in creating utilisateur';

  if (result.affectedRows) {
    message = 'utilisateur created successfully';
  }
  return {message};
}

async function update(id, utilisateur){
  const result = await db.query(
    `UPDATE utilisateurs SET utilisateurs.id=?, utilisateurs.nom_utilisateur=?, Admin.mot_de_passe=? WHERE Admin.id = ?;` ,
    [utilisateur.pk_idAdmin, utilisateur.identifiantAdmin, utilisateur.mdpAdmin, id]  
  );

  let message = 'Error in updating utilisateur';

  if (result.affectedRows) {
    message = 'utilisateur updated successfully';
  }

  return {message};
}

async function remove(id){
  const result = await db.query(
    `DELETE FROM utilisateurs WHERE id=?`,
    [id]
  );

  let message = 'Error in deleting utilisateur';

  if (result.affectedRows) {
    message = 'utilisateur deleted successfully';
  }

  return {message};
}

async function signup(utilisateur){
  const rows = await db.query(
    `INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe) VALUES (?, ?)`,
    [utilisateur.nom_utilisateur, utilisateur.mot_de_passe]  );
  const data = helper.emptyOrRows(rows);

  return {
    data
  }
}

async function login(utilisateur){
  const rows = await db.query(
    `SELECT * FROM utilisateurs WHERE utilisateurs.nom_utilisateur=?`,
    [utilisateur.nom_utilisateur]  );
  const data = helper.emptyOrRows(rows);
  
  return {
    data
  }
}

async function verifyToken(utilisateur){ //ou "decoded"
  const rows = await db.query(
    `SELECT * FROM utilisateurs WHERE utilisateurs.nom_utilisateur=?`,
    [utilisateur.identifiantAdmin]    );
  const data = helper.emptyOrRows(rows);
  
  return{
    data
  }
}

module.exports = {
  getMultiple,
  getOne,
  create,
  update,
  remove,
  signup,
  login,
  verifyToken,
}