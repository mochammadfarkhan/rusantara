const { PythonShell } = require("python-shell");

const cors = require("cors");
const express = require("express");
const app = express();

const { createUser } = require("./src/auth");
const checkIfAuthenticated = require("./middleware/auth-middleware");

const { foods } = require("./src/foods");

const upload = require("./middleware/multer");

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cors());

const getFoodData = (dataToGet) => {
  const data = foods.find((food, index) => {
    if (food.name === dataToGet) return true;
  });

  return data;
};

app.get("/", (req, res) => {
  res.send("API for Rusantara app");
});

app.post("/signup", createUser);

app.get("/foods", checkIfAuthenticated, (req, res) => {
  res.json(foods);
});

app.post(
  "/predict",
  upload.single("image"),
  checkIfAuthenticated,
  (req, res) => {
    const options = {
      args: req.file.filename,
    };

    PythonShell.run("app.py", options, (err, results) => {
      const result = getFoodData(results[0]);
      res.json(result);
    });
  }
);

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
  console.log(`listening on port ${PORT}`);
});
