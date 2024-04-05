const express = require("express");
const router = express.Router();
const httpError = require("http-errors");

router.get("/", (req, res, next) => {
    res.send("OK!")
});

module.exports = router;
