const express = require('express');
const router = express.Router();
const utilisateurs = require("../services/utilisateurs");

const path = require('path')
const dotenv = require('dotenv').config({ path: path.resolve("routes", '../.env') }).parsed;
const JWT_SECRET = dotenv.JWT_SECRET; const JWT_EXPIRES_IN = dotenv.JWT_EXPIRES_IN;
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

/* GET utilisateur */
router.get('/', async function(req, res, next) {
  try {
    res.json(await utilisateurs.getMultiple(req.query.page));
  } catch (err) {
    console.error(`Error while getting utilisateur`, err.message);
    next(err);
  }
});

/* verify token avant le get one car node confond
 la route verify avec un id d'utilisateur */
/* GET user token utilisateur */
router.get('/verify/', async function(req, res, next) {
  const defaultReturnObject = { authenticated: false, user: null };
  try {
    const token = String(req?.headers?.authorization?.replace('Bearer ', ''));
    const decoded = jwt.verify(token, JWT_SECRET);

    const result = await utilisateurs.verifyToken(decoded);

    if (!result) {
      res.status(400).json(defaultReturnObject);
    }
    delete result.mot_de_passe;
    res.status(200).json({ authenticated: true, user: result });
  } catch (err) {
    console.error(`Error while logging in`, err.message);
    res.status(400).send({ error: true, message: err.message });
  }
});

/* GET utilisateur spécifique*/
router.get('/:id', async function(req, res, next) {
  try {
    res.json(await utilisateurs.getOne(req.params.id));
  } catch (err) {
    console.error(`Error while getting utilisateur`, err.message);
    next(err);
  }
});

/* POST utilisateur */
router.post('/', async function(req, res, next) {
  try {
    console.log(req.body);
    res.json(await utilisateurs.create(req.body));
  } catch (err) {
    console.error(`Error while creating utilisateur`, err.message);
    next(err);
  }
});

/* PUT utilisateur */
router.put('/:id', async function(req, res, next) {
  try {
    res.json(await utilisateurs.update(req.params.id, req.body));
  } catch (err) {
    console.error(`Error while updating utilisateur`, err.message);
    next(err);
  }
});

/* DELETE utilisateur */
router.delete('/:id', async function(req, res, next) {
  try {
    res.json(await utilisateurs.remove(req.params.id));
  } catch (err) {
    console.error(`Error while deleting utilisateur`, err.message);
    next(err);
  }
});

/* Signup utilisateur */
router.post('/signup/', async function(req, res, next) {
  try {
    const nom_utilisateur = req.body.nom_utilisateur;
    const mot_de_passe = req.body.mot_de_passe;

    if (!nom_utilisateur || !mot_de_passe || nom_utilisateur==='' || mot_de_passe==='' ) {
      res.status(400).end();
    }
  
    const hashedPassword = await bcrypt.hash(mot_de_passe, 8);
    const userData = {
      nom_utilisateur,
      mot_de_passe: hashedPassword,
    };

    const result = await utilisateurs.signup(userData);
    console.log(result);
    const token = jwt.sign({ user: result.id }, JWT_SECRET, { expiresIn: JWT_EXPIRES_IN });
    console.log(token);

    res.json({token: token});
  } catch (err) {
    console.error(`Error while signing up`, err.message);
    res.status(400).send({ error: true, message: err.message });
  }
});

/* POST login utilisateur */
router.post('/login/', async function(req, res, next) {
  try {
    const nom_utilisateur = req.body.nom_utilisateur;
    const mot_de_passe = req.body.mot_de_passe;
    console.log('' + nom_utilisateur + mot_de_passe);

    if (!nom_utilisateur || !mot_de_passe || nom_utilisateur==='' || mot_de_passe==='' ) {
      res.status(403).end();
    }
    /* Appel bdd: */
    const result = await utilisateurs.login(req.body);

    /* on recup le mdp obtenu dans data, on le renomme, et on le compare a celui dans la base*/
    const hashedPassword = result.data[0].mot_de_passe;
    const isMatch = await bcrypt.compare(mot_de_passe, hashedPassword);
    if (!isMatch) {
      res.status(403).end();
    }
    /*les mdp correspondent, on renvoie le token correspondant a l'Utilisateur:*/
    const token = jwt.sign({
        id: result.data[0].id,
        nom_utilisateur: result.data[0].nom_utilisateur
      },
        JWT_SECRET,
        { expiresIn: JWT_EXPIRES_IN }
    );

    res.json({token: token});
  } catch (err) {
    console.error(`Error while logging in`, err.message);
    //erreur ici cf screen 17/05/2023:
    //res.status(400).send({ error: true, message: err.message });
    next(err);//regle l'erreur
  }
});

module.exports = router;