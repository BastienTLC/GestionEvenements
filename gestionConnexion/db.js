const { Pool } = require('pg');
const config = require('./config');

// Create a new pool using the configuration from `./config.js`
const pool = new Pool(config.db);

async function query(sql, params) {
  let client;
  try {
    // Get a client from the connection pool
    client = await pool.connect();

    // Execute a query on the client
    const res = await client.query(sql, params);
    return res.rows;
  } catch (err) {
    throw err;
  } finally {
    // Make sure to release the client back to the pool even in case of error
    if (client) client.release();
  }
}

module.exports = {
  query
}