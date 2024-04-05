const express = require("express");
const router = express.Router();
const httpError = require("http-errors");

const records = require("../models/records");

router.get("/", (req, res) => {
  res.send("OK!");
});

router.get("/records", async (req, res, next) => {
  try {
    let data = await records.getAll();
    res.send(data);
  } catch (e) {
    next(httpError(500, "Cannot get records.\n" + e));
  }
});

router.post("/records/:name/:score", async (req, res, next) => {
  try {
    let userName = req.params.name;
    let score = req.params.score;
    await records.add(userName, score);
    res.send("Succeed!");
  } catch (e) {
    next(httpError(500, "Cannot add records.\n" + e));
  }
});

module.exports = router;
