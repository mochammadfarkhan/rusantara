const { PythonShell } = require("python-shell");

const cors = require("cors");
const express = require("express");
const app = express();

const { addUser, authenticate } = require("./controller/auth");
const checkIfAuthenticated = require("./middleware/auth-middleware");

const { foods } = require("./resources/foods");

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
  res.json({ message: "API for Rusantara app" });
});

app.post("/signup", addUser);
app.post("/signin", authenticate);
// app.get("/signout", signOut);

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
      res.send(result);
    });
  }
);

const port = process.env.PORT || 8080;
app.listen(port, () => {
  console.log(`listening on port ${port}`);
});
