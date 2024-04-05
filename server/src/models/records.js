let db = require("../utils/database");

async function getAll() {
  let sql = `SELECT (user_name, score) FROM record ORDER BY score DESC`;
  let records = await db.query(sql);
  return records;
}

async function add(name, score) {
  let sql = `INSERT INTO record (user_name, score)
      VALUES ('${name}', ${score})`;
  try {
    await db.query(sql);
  } catch (e) {
    console.error("[Error] Cannot add record to SQLite\n", e);
  }
}

module.exports = { getAll, add };
